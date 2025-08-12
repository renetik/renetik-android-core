package renetik.android.core.lang

import leakcanary.AppWatcher.objectWatcher
import leakcanary.LeakCanary.config
import leakcanary.LeakCanary.showLeakDisplayActivityLauncherIcon
import renetik.android.core.kotlin.then
import renetik.android.core.lang.CSEnvironment.isTestRunner
import renetik.android.core.lang.variable.CSVariable.Companion.variable
import shark.IgnoredReferenceMatcher
import shark.ObjectInspector
import shark.ReferencePattern.InstanceFieldPattern
import shark.ReferencePattern.StaticFieldPattern

val CLASS_PREFIXES = listOf("renetik.android.", "com.renetik.")
val FIELD_CANDIDATES = listOf("id", "key", "name", "title", "text")
val REF_FIELDS = listOf("parent", "preset")

val objectInspector = ObjectInspector { reporter ->
    val heapObject = reporter.heapObject
    val instance = heapObject.asInstance ?: return@ObjectInspector
    val instanceClassName = instance.instanceClassName
    if (!CLASS_PREFIXES.any { instanceClassName.startsWith(it) }) return@ObjectInspector
    val found = mutableSetOf<String>()
    // 1) Primary: scan all instance fields (covers fields declared on superclasses)
    runCatching {
        instance.readFields().forEach { f ->
            val name = f.name
            if (name in FIELD_CANDIDATES) {
                val s = runCatching { f.value.readAsJavaString() }.getOrNull()
                if (!s.isNullOrBlank()) found.add("$name=$s")
            }
        }
    }
    // 2) Secondary: explicit per-class field lookup (keeps your old approach as fallback)
    if (found.isEmpty()) {
        FIELD_CANDIDATES.forEach { fieldName ->
            runCatching {
                val hf = instance[instanceClassName, fieldName] ?: return@runCatching
                val s = hf.value.readAsJavaString()
                if (!s.isNullOrBlank()) found.add("$fieldName=$s")
            }
        }
    }
    // 3) Tertiary: follow one-level refs and inspect their instance fields (preset/parent/owner)
    REF_FIELDS.forEach { refField ->
        runCatching {
            val ref = instance[instanceClassName, refField]?.value?.asObject?.asInstance
            ref?.readFields()?.forEach { field ->
                val fieldName = field.name
                if (fieldName in FIELD_CANDIDATES) {
                    val s = runCatching { field.value.readAsJavaString() }.getOrNull()
                    if (!s.isNullOrBlank()) found.add("$refField.$fieldName=$s")
                }
            }
        }
    }
    // attach labels
    if (found.isNotEmpty()) found.forEach { reporter.labels.add(it) }
}

object CSLeakCanary : CSLeakCanaryInterface {
    override var isEnabled: Boolean by variable(true, ::updateConfiguration)

    override fun Any.expectWeaklyReachable(description: () -> String) {
        if (isEnabled && !isTestRunner) objectWatcher
            .expectWeaklyReachable(this, description())
    }

    override fun enable() = then { isEnabled = true }

    override fun disable() = then { isEnabled = false }

    private val staticFields: List<Pair<String, String>> = listOf(
        "com.mediatek.SbeBoostFrameworkImpl" to "sInstance"
    )

    private val instanceFields: List<Pair<String, String>> = listOf()

    private fun updateConfiguration(isEnabled: Boolean) {
        if (isTestRunner) return
        config = config.copy(dumpHeap = isEnabled,
            referenceMatchers = config.referenceMatchers + staticFields.map {
                IgnoredReferenceMatcher(StaticFieldPattern(it.first, it.second))
            } + instanceFields.map {
                IgnoredReferenceMatcher(InstanceFieldPattern(it.first, it.second))
            },
            objectInspectors = config.objectInspectors + objectInspector
        )
        showLeakDisplayActivityLauncherIcon(isEnabled)
    }

    init {
        updateConfiguration(isEnabled)
    }
}
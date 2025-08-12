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
val FIELD_CANDIDATES = listOf("id", "key", "name", "title", "parent", "text")
val objectInspector = ObjectInspector { reporter ->
    val heapObject = reporter.heapObject
    val instance = heapObject.asInstance ?: return@ObjectInspector
    val instanceClassName = instance.instanceClassName
    if (!CLASS_PREFIXES.any { instanceClassName.startsWith(it) }) return@ObjectInspector
    val found = mutableListOf<String>()
    FIELD_CANDIDATES.forEach { fieldName ->
        runCatching {
            val heapField = instance[instanceClassName, fieldName] ?: return@forEach
            val heapFieldString = heapField.value.readAsJavaString()
            if (!heapFieldString.isNullOrBlank()) found.add("$fieldName=$heapFieldString")
        }
    }
    listOf("parent").forEach { referenceField ->
        runCatching {
            val reference = instance[instanceClassName, referenceField]
                ?.value?.asObject?.asInstance
            if (reference != null) FIELD_CANDIDATES.forEach { candidate ->
                val heapField = reference[reference.instanceClassName, candidate]
                val heapFieldString = heapField?.value?.readAsJavaString()
                if (!heapFieldString.isNullOrBlank())
                    found.add("$referenceField.$candidate=$heapFieldString")
            }
        }
    }
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
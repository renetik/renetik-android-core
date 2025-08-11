package renetik.android.core.lang

import leakcanary.AppWatcher.objectWatcher
import leakcanary.LeakCanary.config
import leakcanary.LeakCanary.showLeakDisplayActivityLauncherIcon
import renetik.android.core.kotlin.then
import renetik.android.core.lang.CSEnvironment.isTestRunner
import renetik.android.core.lang.variable.CSVariable.Companion.variable
import shark.IgnoredReferenceMatcher
import shark.ReferencePattern.InstanceFieldPattern
import shark.ReferencePattern.StaticFieldPattern

object CSLeakCanary : CSLeakCanaryInterface {
    override var isEnabled: Boolean by variable(true, ::updateConfiguration)

    override fun Any.expectWeaklyReachable(description: String) {
        if (isEnabled && !isTestRunner)
            objectWatcher.expectWeaklyReachable(this, description)
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
            }
        )
        showLeakDisplayActivityLauncherIcon(isEnabled)
    }

    init {
        updateConfiguration(isEnabled)
    }
}
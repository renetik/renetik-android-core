package renetik.android.core.lang

import leakcanary.AppWatcher.objectWatcher
import leakcanary.LeakCanary.config
import leakcanary.LeakCanary.showLeakDisplayActivityLauncherIcon
import renetik.android.core.kotlin.run
import renetik.android.core.lang.CSEnvironment.isTestRunner
import renetik.android.core.lang.variable.CSVariable.Companion.variable

object CSLeakCanary : CSLeakCanaryInterface {
    private var enabled by variable(false, ::updateConfiguration)

    override fun Any.expectWeaklyReachable(description: String) {
        if (!isTestRunner && enabled)
            objectWatcher.expectWeaklyReachable(this, description)
    }

    override fun enabled() = run { enabled = true }

    override fun disabled() = run { enabled = false }

    private fun updateConfiguration(isEnabled: Boolean) {
        if (isTestRunner) return
        config = config.copy(dumpHeap = isEnabled)
        showLeakDisplayActivityLauncherIcon(isEnabled)
    }
}
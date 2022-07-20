package renetik.android.core.lang

import leakcanary.AppWatcher.objectWatcher
import renetik.android.core.lang.CSEnvironment.isTestRunner

object CSLeakCanary {
    fun Any.expectWeaklyReachable(description: String) {
        if (!isTestRunner)
            objectWatcher.expectWeaklyReachable(this, description)
    }
}
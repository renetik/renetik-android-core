package renetik.android.core.base

import android.app.Application
import renetik.android.core.lang.CSEnvironment
import renetik.android.core.logging.CSLog.logInfo
import renetik.android.core.logging.CSLog.logWarn

abstract class CSApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        CSEnvironment.app = this
    }

    override fun onLowMemory() {
        super.onLowMemory()
        logWarn("onLowMemory")
    }

    override fun onTerminate() {
        super.onTerminate()
        logInfo("onTerminate")
    }
}
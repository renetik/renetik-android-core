package renetik.android.core.base

import android.app.Application
import renetik.android.core.logging.CSLog.logInfo
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.core.logging.CSLogMessage.Companion.message

abstract class CSApplication : Application() {
    override fun onLowMemory() {
        super.onLowMemory()
        logWarn { message("onLowMemory") }
    }

    override fun onTerminate() {
        super.onTerminate()
        logInfo { message("onTerminate") }
    }
}
package renetik.android.core.lang

import android.os.Handler
import android.os.Looper.getMainLooper
import renetik.android.core.logging.CSLog.logWarn

object CSHandler {
    val mainHandler by lazy { Handler(getMainLooper()) }

    fun postOnMain(function: () -> Unit) {
        if (!mainHandler.post(function))
            logWarn { "Runnable not run" }
    }

    fun postOnMain(after: Long, function: () -> Unit) {
        if (!mainHandler.postDelayed(function, after))
            logWarn { "Runnable not run" }
    }

    fun postOnMain(after: Int = 0, function: () -> Unit) =
        postOnMain(after.toLong(), function)
}
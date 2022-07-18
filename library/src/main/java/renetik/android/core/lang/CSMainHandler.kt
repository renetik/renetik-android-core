package renetik.android.core.lang

import android.os.Handler
import android.os.Looper.getMainLooper
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.core.logging.CSLogMessage.Companion.message

object CSMainHandler {
    val mainHandler by lazy { Handler(getMainLooper()) }

    fun postOnMain(function: () -> Unit) {
        if (!mainHandler.post(function))
            logWarn { message("Runnable not run") }
    }

    fun postOnMain(delay: Long, function: () -> Unit) {
        if (!mainHandler.postDelayed(function, delay))
            logWarn { message("Runnable not run") }
    }

    fun postOnMain(delay: Int, function: () -> Unit) =
        postOnMain(delay.toLong(), function)

    fun removePosted(function: () -> Unit) =
        mainHandler.removeCallbacks(function)
}
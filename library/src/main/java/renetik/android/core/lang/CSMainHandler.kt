package renetik.android.core.lang

import android.os.Handler
import android.os.Looper.getMainLooper
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.core.logging.CSLogMessage.Companion.message

object CSMainHandler {
    val handler by lazy { Handler(getMainLooper()) }

    fun postOnMain(function: () -> Unit) {
        if (!handler.post(function))
            logWarn { message("Runnable not run") }
    }

    fun postOnMain(delay: Long, function: () -> Unit) {
        if (!handler.postDelayed(function, delay))
            logWarn { message("Runnable not run") }
    }

    fun postOnMain(delay: Int, function: () -> Unit) =
        postOnMain(delay.toLong(), function)

    fun removePosted(function: () -> Unit) =
        handler.removeCallbacks(function)
}
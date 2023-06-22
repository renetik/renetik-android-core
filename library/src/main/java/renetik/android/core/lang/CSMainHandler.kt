package renetik.android.core.lang

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper.getMainLooper
import renetik.android.core.logging.CSLog.logWarn

object CSMainHandler {
    val backgroundHandler by lazy {
        HandlerThread("CSMainHandler threadHandler").run {
            start(); Handler(looper)
        }
    }
    val mainHandler by lazy {
        Handler(getMainLooper())
    }

    fun postOnMain(function: () -> Unit) {
        if (!mainHandler.post(function))
            logWarn { "Runnable not run" }
    }

    fun postOnMain(delay: Long, function: () -> Unit) {
        if (!mainHandler.postDelayed(function, delay))
            logWarn { "Runnable not run" }
    }

    fun postOnMain(delay: Int, function: () -> Unit) =
        postOnMain(delay.toLong(), function)

    fun removePosted(function: () -> Unit) =
        mainHandler.removeCallbacks(function)
}
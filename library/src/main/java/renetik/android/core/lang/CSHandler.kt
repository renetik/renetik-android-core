package renetik.android.core.lang

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper.getMainLooper

object CSHandler {
    val threadHandler: Handler by lazy {
        HandlerThread("CSHandler background").run { start(); Handler(looper) }
    }
    val mainHandler by lazy {
        Handler(getMainLooper())
    }
}
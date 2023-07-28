package renetik.android.core.lang

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper.getMainLooper

object CSHandler {
    val background: Handler by lazy {
        HandlerThread("CSHandler background").run { start(); Handler(looper) }
    }
    val main by lazy {
        Handler(getMainLooper())
    }
}
package renetik.android.core.java.lang

import renetik.android.core.lang.CSTimeConstants.MilliToNanoSecond
import java.lang.Thread.sleep

object CSThread {
    val currentThread: Thread get() = Thread.currentThread()

    fun sleepNano(value: Long) {
        val millis = value / MilliToNanoSecond
        val nanos = value % MilliToNanoSecond
        sleep(millis.toLong(), nanos.toInt())
    }

    fun sleepNano(value: Float) {
        val millis = value / MilliToNanoSecond
        val nanos = value % MilliToNanoSecond
        sleep(millis.toLong(), nanos.toInt())
    }

}
package renetik.android.core.java.lang

import renetik.android.core.lang.CSTimeConstants.MilliToNanoSecondMultiplier

object CSThread {
    val currentThread: Thread get() = Thread.currentThread()

    fun sleepNano(value: Long) {
        val millis = value / MilliToNanoSecondMultiplier
        val nanos = value % MilliToNanoSecondMultiplier
        Thread.sleep(millis, nanos.toInt())
    }

    fun sleepNano(value: Float) {
        val millis = value / MilliToNanoSecondMultiplier
        val nanos = value % MilliToNanoSecondMultiplier
        Thread.sleep(millis.toLong(), nanos.toInt())
    }
}
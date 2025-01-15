package renetik.android.core.java.lang

import java.lang.Thread.sleep

object CSThread {
    val currentThread: Thread get() = Thread.currentThread()

    fun sleepNano(value: Long) {
        val millis = value / 1_000_000
        val nanos = (value % 1_000_000).toInt()
        sleep(millis, nanos)
    }

}
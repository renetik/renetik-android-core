package renetik.android.core.lang

import android.os.HandlerThread
import androidx.annotation.WorkerThread
import renetik.android.core.java.util.concurrent.background
import renetik.android.core.java.util.concurrent.backgroundNano
import renetik.android.core.java.util.concurrent.backgroundRepeat
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.ScheduledThreadPoolExecutor


object CSBackground {
    val executor = ScheduledThreadPoolExecutor(3)

//    val handler by lazy {
//        val handlerThread = HandlerThread("backgroundThread")
//        if (!handlerThread.isAlive) handlerThread.start()
//        handlerThread
//    }

    fun background(
        @WorkerThread function: Func
    ): ScheduledFuture<*> =
        executor.background(function = function)

    fun background(
        delay: Long = 0, @WorkerThread function: () -> Unit
    ): ScheduledFuture<*> =
        executor.background(delay = delay, function = function)

    fun backgroundNano(
        delay: Long = 0, @WorkerThread function: () -> Unit
    ): ScheduledFuture<*> =
        executor.backgroundNano(delay = delay, function = function)

    fun background(
        delay: Int = 0, @WorkerThread function: () -> Unit
    ): ScheduledFuture<*> =
        executor.background(delay = delay.toLong(), function = function)

    fun backgroundRepeat(
        delay: Long, period: Long, @WorkerThread function: () -> Unit
    ): ScheduledFuture<*> =
        executor.backgroundRepeat(delay = delay, period = period, function = function)

    fun backgroundRepeat(
        period: Long, @WorkerThread function: () -> Unit
    ): ScheduledFuture<*> =
        executor.backgroundRepeat(period, function)
}


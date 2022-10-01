package renetik.android.core.lang

import renetik.android.core.java.util.concurrent.background
import renetik.android.core.java.util.concurrent.backgroundNano
import renetik.android.core.java.util.concurrent.backgroundRepeat
import java.util.concurrent.Executors.newSingleThreadScheduledExecutor
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture

object CSBackground {
     val executor: ScheduledExecutorService = newSingleThreadScheduledExecutor()

    fun background(function: Func): ScheduledFuture<*> =
        executor.background(function = function)

    fun background(delay: Long = 0, function: () -> Unit): ScheduledFuture<*> =
        executor.background(delay = delay, function = function)

    fun backgroundNano(delay: Long = 0, function: () -> Unit): ScheduledFuture<*> =
        executor.backgroundNano(delay = delay, function = function)

    fun background(delay: Int = 0, function: () -> Unit): ScheduledFuture<*> =
        executor.background(delay = delay.toLong(), function = function)

    fun backgroundRepeat(delay: Long, period: Long, function: () -> Unit): ScheduledFuture<*> =
        executor.backgroundRepeat(delay = delay, period = period, function = function)

    fun backgroundRepeat(period: Long, function: () -> Unit): ScheduledFuture<*> =
        executor.backgroundRepeat(period, function)
}


package renetik.android.core.java.util

import renetik.android.core.java.util.concurrent.*
import renetik.android.core.lang.CSMainHandler.postOnMain
import renetik.android.core.lang.catchAllError
import renetik.android.core.lang.Func
import java.util.*
import java.util.concurrent.Executors.newSingleThreadScheduledExecutor
import java.util.concurrent.ScheduledExecutorService
import kotlin.concurrent.timerTask

object CSTimer {

    val executor: ScheduledExecutorService = newSingleThreadScheduledExecutor()

    fun scheduleAtFixedRateRunOnUI(delay: Long = 0, period: Long, function: Func) =
        executor.backgroundRepeatRunOnUI(delay, period, function)

    fun scheduleAtFixedRate(delay: Long = 0, period: Long, function: Func) =
        executor.backgroundRepeat(delay, period, function)

    fun scheduleRunOnUI(delay: Long = 0, function: Func) =
        executor.backgroundRunOnUi(delay, function)

    fun schedule(delay: Long = 0, function: Func) =
        executor.background(delay, function)

    fun scheduleNano(delay: Long = 0, function: Func) =
        executor.backgroundNano(delay, function)
}

fun Timer.scheduleAtFixedRateRunOnUI(delay: Long = 0, period: Long, function: Func) =
    scheduleAtFixedRate(delay, period) { postOnMain(function) }

inline fun Timer.scheduleAtFixedRate(delay: Long = 0, period: Long,
                                     crossinline function: Func): TimerTask {
    val task = timerTask { catchAllError { function() } }
    scheduleAtFixedRate(task, delay, period)
    return task
}

inline fun Timer.schedule(delay: Long = 0,
                          crossinline function: Func): TimerTask {
    val task = timerTask { catchAllError { function() } }
    schedule(task, delay)
    return task
}


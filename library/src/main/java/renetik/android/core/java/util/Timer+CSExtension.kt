package renetik.android.core.java.util

import renetik.android.core.lang.CSMainHandler.postOnMain
import renetik.android.core.lang.Func
import renetik.android.core.lang.catchAllError
import java.util.*
import kotlin.concurrent.timerTask

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


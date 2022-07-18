package renetik.android.core.java

import renetik.android.core.lang.CSTimeConstants.MilliToNanoSecondMultiplier
import renetik.android.core.lang.CSTimeConstants.Second
import renetik.android.core.logging.CSLog.logDebug
import renetik.android.core.logging.CSLogMessage.Companion.message
import java.lang.System.nanoTime

fun measureTimeElapsed(function: () -> Unit) {
    val time = nanoTime()
    function()
    val duration = nanoTime() - time
    logDebug { message("Duration ${duration / MilliToNanoSecondMultiplier / Second} Seconds") }
}
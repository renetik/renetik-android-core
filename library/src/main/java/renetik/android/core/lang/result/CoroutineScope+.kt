@file:OptIn(ExperimentalCoroutinesApi::class)

package renetik.android.core.lang.result

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.debug.DebugProbes
import kotlinx.coroutines.withTimeoutOrNull
import renetik.android.core.lang.CSEnvironment.isCoroutinesDebug
import renetik.android.core.logging.CSLog.logWarn
import java.util.concurrent.CancellationException
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

val mainScope: CoroutineScope = MainScope()

suspend fun coroutinesShutDown(timeout: Duration = 5.seconds) {
    val job = mainScope.coroutineContext[Job] ?: return
    job.cancel(CancellationException("MainScope Shutdown"))
    withTimeoutOrNull(timeout) { job.join() } ?: run {
        logWarn("Timeout waiting for mainScope coroutines to finish")
        if (isCoroutinesDebug) DebugProbes.dumpCoroutines()
    }
}
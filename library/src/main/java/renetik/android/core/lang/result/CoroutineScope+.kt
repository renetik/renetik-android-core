package renetik.android.core.lang.result

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.withTimeoutOrNull
import renetik.android.core.logging.CSLog.logWarn
import java.util.concurrent.CancellationException
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

val mainScope: CoroutineScope = MainScope()

suspend fun coroutinesShutDown(timeout: Duration = 5.seconds) {
    val job = mainScope.coroutineContext[Job] ?: return
    job.cancel(CancellationException("MainScope Shutdown"))
    withTimeoutOrNull(timeout) { job.join() }
        ?: logWarn("Timeout waiting for mainScope coroutines to finish")
}
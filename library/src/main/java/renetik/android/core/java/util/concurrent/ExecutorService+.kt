package renetik.android.core.java.util.concurrent

import kotlinx.coroutines.invoke
import renetik.android.core.base.CSApplication.Companion.app
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

fun ExecutorService.shutdownAndWait(second: Int = 10) {
    shutdown()
    awaitTermination(second.toLong(), TimeUnit.SECONDS)
}

suspend fun ExecutorService.shutdownWaiting(
    timeout: Duration = 10.seconds
): Boolean = app.IO {
    shutdown()
    awaitTermination(timeout.inWholeMilliseconds, TimeUnit.MILLISECONDS)
}
package renetik.android.core.java.util.concurrent

import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit

fun ExecutorService.shutdownAndWait(second: Int = 10) {
    shutdown()
    awaitTermination(second.toLong(), TimeUnit.SECONDS)
}
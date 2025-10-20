package renetik.android.core.lang.result

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.job
import kotlinx.coroutines.withTimeoutOrNull
import renetik.android.core.lang.CSEnvironment.isCoroutinesDebug
import renetik.android.core.logging.CSLog.logWarn
import java.util.concurrent.CancellationException
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

var mainScope: CoroutineScope = MainScope()

suspend fun coroutinesShutDown(timeout: Duration = 5.seconds) {
    val job = mainScope.coroutineContext.job
    job.cancel(CancellationException("MainScope Shutdown"))
    withTimeoutOrNull(timeout) { job.join() } ?: run {
        logWarn("Timeout waiting for mainScope coroutines to finish")
        if (isCoroutinesDebug) job.dumpJobTree()
    }
}

fun Job.dumpJobTree() {
    fun Job.dump(indent: String) {
        val name = (this as? CoroutineScope)?.coroutineContext
            ?.get(CoroutineName)?.name ?: toString()
        val state = "active=${isActive} completed=${isCompleted} cancelled=${isCancelled}"
        logWarn("MainScope Job: $indent$name — $state")
        for (child in children) child.dump("$indent  ")
    }
    dump("")
}
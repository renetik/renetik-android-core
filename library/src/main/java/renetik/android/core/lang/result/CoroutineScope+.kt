package renetik.android.core.lang.result

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.job
import kotlinx.coroutines.withTimeoutOrNull
import renetik.android.core.base.CSApplication
import renetik.android.core.lang.CSEnvironment.isCoroutinesDebug
import renetik.android.core.logging.CSLog.logInfo
import renetik.android.core.logging.CSLog.logWarn
import java.util.concurrent.CancellationException
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

var mainScope: CoroutineScope = createMainScope()

fun createMainScope() = CSApplication.app.scope.createSupervisorChild()

fun CoroutineScope.createSupervisorChild(): CoroutineScope {
    val job = coroutineContext[Job] ?: error("scope lacks Job")
    return CoroutineScope(coroutineContext + SupervisorJob(parent = job))
}

@Suppress("SuspendFunctionOnCoroutineScope")
suspend fun CoroutineScope.shutDown(timeout: Duration = 5.seconds) {
    logInfo()
    val job = coroutineContext.job
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
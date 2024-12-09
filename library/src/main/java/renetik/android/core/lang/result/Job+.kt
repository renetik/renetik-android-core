package renetik.android.core.lang.result

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

suspend fun Job.cancelIfNotActive(scope: CoroutineScope, onCancel: () -> Unit) = apply {
    while (isActive) {
        delay(500)
        if (!scope.isActive) {
            onCancel()
            cancelAndJoin()
        }
    }
    join()
}
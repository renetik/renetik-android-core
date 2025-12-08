package renetik.android.core.lang.result

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineContext.limitedParallelism(parallelism: Int) =
    (this[ContinuationInterceptor] as CoroutineDispatcher)
        .limitedParallelism(parallelism)

fun CoroutineContext.named(name: String): CoroutineContext =
    this + CoroutineName(name)

suspend inline operator fun <T> CoroutineContext.invoke(
    crossinline block: suspend () -> T
): T = withContext(this) { block() }

@JvmName("CoroutineContextNullInvoke")
suspend inline operator fun <T> CoroutineContext?.invoke(
    crossinline block: suspend () -> T
): T = this?.let { withContext(this) { block() } } ?: block()
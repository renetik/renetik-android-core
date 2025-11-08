package renetik.android.core.lang.result

import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

suspend inline operator fun <T> CoroutineContext.invoke(
    crossinline block: suspend () -> T
): T = withContext(this) { block() }

@JvmName("CoroutineContextNullInvoke")
suspend inline operator fun <T> CoroutineContext?.invoke(
    crossinline block: suspend () -> T
): T = this?.let {  withContext(this) { block() } } ?: block()
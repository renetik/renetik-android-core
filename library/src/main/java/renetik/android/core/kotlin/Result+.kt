package renetik.android.core.kotlin

import kotlinx.coroutines.CancellationException

inline fun <T, R> T.coroutineCatch(block: T.() -> R) = runCatching { block(this) }
    .onFailureOf<CancellationException> { throw it }

inline fun <reified T : Throwable> Result<*>.onFailureOf(onFailure: (T) -> Unit) = apply {
    exceptionOrNull()?.also { if (it is T) onFailure(it) }
}
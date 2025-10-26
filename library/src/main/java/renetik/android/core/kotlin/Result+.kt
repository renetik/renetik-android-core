@file:Suppress("NOTHING_TO_INLINE")

package renetik.android.core.kotlin

import kotlinx.coroutines.CancellationException

inline fun <reified T : Throwable> Result<*>.onFailureOf(onFailure: (T) -> Unit) = apply {
    exceptionOrNull()?.also { if (it is T) onFailure(it) }
}

inline fun Result<*>.throwCancellation() = onFailureOf<CancellationException> { throw it }
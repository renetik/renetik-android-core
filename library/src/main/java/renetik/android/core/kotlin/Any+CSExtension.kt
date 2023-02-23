package renetik.android.core.kotlin

import renetik.android.core.lang.CSHasId

inline fun <T, R> onNotNull(receiver: T?, block: T.() -> R) {
    receiver?.let { receiver.block() }
}

inline fun <T : Any> T.then(function: (T) -> Unit): Unit = function(this)

inline fun <T : Any> T.changeIf(condition: Boolean, function: (T) -> T) =
    if (condition) function(this) else this

inline val <T : Any> T.className get() = this::class.simpleName

fun Any?.toId() = (this as? CSHasId)?.id ?: toString()


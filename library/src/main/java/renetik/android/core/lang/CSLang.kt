package renetik.android.core.lang

object CSLang {
    val EmptyFunc = {}
}

inline fun <T : Any, R> synchronized(lock: T, block: (T) -> R): R =
    kotlin.synchronized(lock) { block(lock) }

inline fun invoke(block: () -> Unit): Unit = block()
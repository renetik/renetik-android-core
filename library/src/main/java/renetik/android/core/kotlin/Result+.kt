package renetik.android.core.kotlin

inline fun <reified T : Error> Result<*>.onFailureOf(onFailure: (T) -> Unit) = apply {
    exceptionOrNull()?.also { if (it is T) onFailure(it) }
}

inline fun <reified T : Throwable> Result<*>.onFailureOf(onFailure: (T) -> Unit) = apply {
    exceptionOrNull()?.also { if (it is T) onFailure(it) }
}
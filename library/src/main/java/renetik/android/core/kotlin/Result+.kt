package renetik.android.core.kotlin

inline fun <reified T : Exception> Result<*>.onFailureOf(onFailure: (T) -> Unit) = apply {
    exceptionOrNull()?.also { if (it is T) onFailure(it) }
}
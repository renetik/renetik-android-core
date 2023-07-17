package renetik.android.core.lang.lazy

interface CSLazyProperty<T> {
    val isInitialized: Boolean
    val value: T?
    fun reset()
}
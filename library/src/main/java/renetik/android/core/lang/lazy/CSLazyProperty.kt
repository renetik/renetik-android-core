package renetik.android.core.lang.lazy

interface CSLazyProperty<T> { // TODO: make implement Lazy<T>
    val isInitialized: Boolean
    val value: T?
    fun reset()
}
package renetik.android.core.lang.lazy

interface CSLazyProperty {
    val isInitialized: Boolean
    fun reset()
}
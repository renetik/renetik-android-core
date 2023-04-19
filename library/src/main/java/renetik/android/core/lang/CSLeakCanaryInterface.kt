package renetik.android.core.lang

interface CSLeakCanaryInterface {
    fun Any.expectWeaklyReachable(description: String) = Unit
    val enabled: Boolean get() = false
    fun enable() = Unit
    fun disable() = Unit
}
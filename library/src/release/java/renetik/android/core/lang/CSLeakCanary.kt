package renetik.android.core.lang

object CSLeakCanary {
    fun Any.expectWeaklyReachable(description: String) = Unit
    fun enable() = Unit
    fun disable() = Unit
}
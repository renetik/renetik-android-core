package renetik.android.core.lang

interface CSLeakCanaryInterface {
    fun Any.expectWeaklyReachable(description: String) = Unit
    fun enabled() = Unit
    fun disabled() = Unit
}
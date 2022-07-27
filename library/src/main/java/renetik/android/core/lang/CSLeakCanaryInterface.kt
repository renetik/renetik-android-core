package renetik.android.core.lang

import renetik.android.core.kotlin.primitives.ifTrue

interface CSLeakCanaryInterface {
    fun Any.expectWeaklyReachable(description: String) = Unit
    fun enabled() = Unit
    fun disabled() = Unit
}
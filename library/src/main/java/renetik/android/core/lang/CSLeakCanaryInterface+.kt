package renetik.android.core.lang

import renetik.android.core.kotlin.primitives.ifTrue

fun CSLeakCanaryInterface.active(isActive: Boolean) =
    isActive.ifTrue(::enabled).elseDo(::disabled)
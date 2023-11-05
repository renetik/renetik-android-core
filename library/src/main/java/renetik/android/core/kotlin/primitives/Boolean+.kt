package renetik.android.core.kotlin.primitives

import renetik.android.core.lang.Func

val Boolean.Companion.random get() = Int.random(0, 1) == 1

val Boolean?.isTrue: Boolean get() = this == true
val Boolean?.isFalse: Boolean get() = this == false

inline fun <T : Boolean?> T.isTrue(function: Func) {
    if (isTrue) function()
}

inline fun <T : Boolean?> T.isFalse(function: Func) {
    if (isFalse) function()
}

inline fun <T : Boolean?, R> T.ifTrue(function: () -> R): R? =
    if (isTrue) function() else null

inline fun <T : Boolean?, R> T.ifFalse(function: () -> R): R? =
    if (isFalse) function() else null
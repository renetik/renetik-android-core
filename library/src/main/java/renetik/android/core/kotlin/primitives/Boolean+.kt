package renetik.android.core.kotlin.primitives

import renetik.android.core.lang.CSConditionalResult
import renetik.android.core.lang.Func
import renetik.android.core.lang.ReturnFunc

val Boolean.Companion.random get() = Int.random(0, 1) == 1

val <T : Boolean?> T.isTrue: Boolean get() = this == true
val <T : Boolean?> T.isFalse: Boolean get() = this == false
val <T : Boolean?> T.isNotTrue: Boolean get() = this != true
val <T : Boolean?> T.isNotFalse: Boolean get() = this != false

inline fun <T : Boolean?> T.isTrue(function: Func) {
    if (isTrue) function()
}

inline fun <T : Boolean?> T.isFalse(function: Func) {
    if (isFalse) function()
}

inline fun <T : Boolean?> T.ifTrue(function: Func): CSConditionalResult {
    if (isTrue) function()
    return CSConditionalResult(!isTrue)
}

inline fun <T : Boolean?> T.ifNotTrue(function: Func): CSConditionalResult {
    if (isNotTrue) function()
    return CSConditionalResult(!isNotTrue)
}

inline fun <T : Boolean?> T.ifFalse(function: Func): CSConditionalResult {
    if (isFalse) function()
    return CSConditionalResult(!isFalse)
}

inline fun <T : Boolean?> T.ifNotFalse(function: Func): CSConditionalResult {
    if (isNotFalse) function()
    return CSConditionalResult(!isNotFalse)
}
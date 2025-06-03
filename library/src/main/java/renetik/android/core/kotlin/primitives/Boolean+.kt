package renetik.android.core.kotlin.primitives

import renetik.android.core.lang.Func
import renetik.android.core.lang.value.CSValue
import renetik.android.core.lang.value.isTrue

val Boolean.Companion.random get() = Int.random(0, 1) == 1

val Boolean?.isTrue: Boolean get() = this == true
val Boolean?.isFalse: Boolean get() = this == false
val Boolean?.isFalseOrNull: Boolean get() = this == null || this == false

// 1) “or” overloads

// (A) No clash here: receiver = primitive Boolean, param = boxed Boolean?
infix fun Boolean.or(boolean: Boolean?) = isTrue || boolean.isTrue

// (B) Clash group #1: receiver = primitive Boolean, param = CSValue<X>  (X = Boolean, Boolean?, or nullable CSValue<Boolean>)
//     All three erase to: (boolean, CSValue) → boolean
@JvmName("or_CSValueOfBoolean")
infix fun Boolean.or(boolean: CSValue<Boolean>) = this || boolean.value

@JvmName("or_CSValueOfNullableBoolean")
infix fun Boolean.or(boolean: CSValue<Boolean?>) = this || boolean.isTrue

@JvmName("or_NullableCSValueOfBoolean")
infix fun Boolean.or(boolean: CSValue<Boolean>?) = this || boolean.isTrue

// (C) No clash here: receiver = boxed Boolean (Boolean?), param = primitive Boolean
infix fun Boolean?.or(boolean: Boolean) = isTrue || boolean

// (D) Clash group #2: receiver = boxed Boolean, param = CSValue<X>  (X = Boolean, Boolean?, or nullable CSValue<Boolean>)
//     All three erase to: (java.lang.Boolean, CSValue) → boolean
@JvmName("orBoxed_CSValueOfBoolean")
infix fun Boolean?.or(boolean: CSValue<Boolean>) = isTrue || boolean.value

@JvmName("orBoxed_CSValueOfNullableBoolean")
infix fun Boolean?.or(boolean: CSValue<Boolean?>) = isTrue || boolean.isTrue

@JvmName("orBoxed_NullableCSValueOfBoolean")
infix fun Boolean?.or(boolean: CSValue<Boolean>?) = isTrue || boolean.isTrue

infix fun Boolean.and(boolean: Boolean?) = isTrue && boolean.isTrue

@JvmName("and_CSValueOfBoolean")
infix fun Boolean.and(boolean: CSValue<Boolean>) = this && boolean.value

@JvmName("and_CSValueOfNullableBoolean")
infix fun Boolean.and(boolean: CSValue<Boolean?>) = this && boolean.isTrue

@JvmName("and_NullableCSValueOfBoolean")
infix fun Boolean.and(boolean: CSValue<Boolean>?) = this && boolean.isTrue

infix fun Boolean?.and(boolean: Boolean) = isTrue && boolean

@JvmName("andBoxed_CSValueOfBoolean")
infix fun Boolean?.and(boolean: CSValue<Boolean>) = isTrue && boolean.value

@JvmName("andBoxed_CSValueOfNullableBoolean")
infix fun Boolean?.and(boolean: CSValue<Boolean?>) = isTrue && boolean.isTrue

@JvmName("andBoxed_NullableCSValueOfBoolean")
infix fun Boolean?.and(boolean: CSValue<Boolean>?) = isTrue && boolean.isTrue


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
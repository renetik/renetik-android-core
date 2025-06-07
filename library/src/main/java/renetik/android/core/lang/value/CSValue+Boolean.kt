@file:Suppress("NOTHING_TO_INLINE")

package renetik.android.core.lang.value

import renetik.android.core.kotlin.primitives.isTrue
import renetik.android.core.kotlin.then
import renetik.android.core.lang.Func

inline val CSValue<Boolean>.isTrue: Boolean get() = value

@get:JvmName("CSValueBooleanOptionalIsTrue")
inline val CSValue<Boolean>?.isTrue: Boolean get() = this?.value == true

@get:JvmName("CSValueOptionalBooleanIsTrue")
inline val CSValue<Boolean?>.isTrue: Boolean get() = this.value == true

inline val CSValue<Boolean>.isFalse get() = !value

inline fun <R> CSValue<Boolean>.ifTrue(function: () -> R): R? =
    if (isTrue) function() else null

inline fun <R> CSValue<Boolean>.ifFalse(function: () -> R): R? =
    if (isFalse) function() else null

@JvmName("isTrueBooleanNullable")
fun CSValue<Boolean?>.isTrue(): Boolean = value == true

inline fun CSValue<Boolean>.isTrue(function: Func) = then { if (isTrue) function() }
inline fun CSValue<Boolean>.isFalse(function: Func) = then { if (isFalse) function() }

@JvmName("or_CSValueOfBoolean")
infix fun Boolean.or(boolean: CSValue<Boolean>) = this || boolean.value

@JvmName("or_CSValueOfNullableBoolean")
infix fun Boolean.or(boolean: CSValue<Boolean?>) = this || boolean.isTrue

@JvmName("or_NullableCSValueOfBoolean")
infix fun Boolean.or(boolean: CSValue<Boolean>?) = this || boolean.isTrue

@JvmName("orBoxed_CSValueOfBoolean")
infix fun Boolean?.or(boolean: CSValue<Boolean>) = isTrue || boolean.value

@JvmName("orBoxed_CSValueOfNullableBoolean")
infix fun Boolean?.or(boolean: CSValue<Boolean?>) = isTrue || boolean.isTrue

@JvmName("orBoxed_NullableCSValueOfBoolean")
infix fun Boolean?.or(boolean: CSValue<Boolean>?) = isTrue || boolean.isTrue

@JvmName("and_CSValueOfBoolean")
infix fun Boolean.and(boolean: CSValue<Boolean>) = this && boolean.value

@JvmName("and_CSValueOfNullableBoolean")
infix fun Boolean.and(boolean: CSValue<Boolean?>) = this && boolean.isTrue

@JvmName("and_NullableCSValueOfBoolean")
infix fun Boolean.and(boolean: CSValue<Boolean>?) = this && boolean.isTrue

@JvmName("andBoxed_CSValueOfBoolean")
infix fun Boolean?.and(boolean: CSValue<Boolean>) = isTrue && boolean.value

@JvmName("andBoxed_CSValueOfNullableBoolean")
infix fun Boolean?.and(boolean: CSValue<Boolean?>) = isTrue && boolean.isTrue

@JvmName("andBoxed_NullableCSValueOfBoolean")
infix fun Boolean?.and(boolean: CSValue<Boolean>?) = isTrue && boolean.isTrue

infix fun CSValue<Boolean>.and(second: Boolean): Boolean = value && second
infix fun CSValue<Boolean>.or(second: Boolean): Boolean = value || second
infix fun CSValue<Boolean>.or(second: CSValue<Boolean>): Boolean = value || second.value


// Left = CSValue<Boolean?> (non-null reference, but value may be null)
@JvmName("and_CSVNullable_left_boolean")
infix fun CSValue<Boolean?>.and(second: Boolean): Boolean =
    this.isTrue && second

@JvmName("or_CSVNullable_left_boolean")
infix fun CSValue<Boolean?>.or(second: Boolean): Boolean =
    this.isTrue || second

@JvmName("and_CSVNullable_left_CSVBoolean")
infix fun CSValue<Boolean?>.and(second: CSValue<Boolean>): Boolean =
    this.isTrue && second.value

@JvmName("or_CSVNullable_left_CSVBoolean")
infix fun CSValue<Boolean?>.or(second: CSValue<Boolean>): Boolean =
    this.isTrue || second.value

@JvmName("and_CSVNullable_left_CSVNullableBoolean")
infix fun CSValue<Boolean?>.and(second: CSValue<Boolean?>): Boolean =
    this.isTrue && second.isTrue

@JvmName("or_CSVNullable_left_CSVNullableBoolean")
infix fun CSValue<Boolean?>.or(second: CSValue<Boolean?>): Boolean =
    this.isTrue || second.isTrue

// Left = CSValue<Boolean>? (the container itself may be null)
@JvmName("and_nullableCSV_left_boolean")
infix fun CSValue<Boolean>?.and(second: Boolean): Boolean =
    this.isTrue && second

@JvmName("or_nullableCSV_left_boolean")
infix fun CSValue<Boolean>?.or(second: Boolean): Boolean =
    this.isTrue || second

@JvmName("and_nullableCSV_left_CSVBoolean")
infix fun CSValue<Boolean>?.and(second: CSValue<Boolean>): Boolean =
    this.isTrue && second.value

@JvmName("or_nullableCSV_left_CSVBoolean")
infix fun CSValue<Boolean>?.or(second: CSValue<Boolean>): Boolean =
    this.isTrue || second.value

@JvmName("and_nullableCSV_left_CSVNullableBoolean")
infix fun CSValue<Boolean>?.and(second: CSValue<Boolean?>): Boolean =
    this.isTrue && second.isTrue

@JvmName("or_nullableCSV_left_CSVNullableBoolean")
infix fun CSValue<Boolean>?.or(second: CSValue<Boolean?>): Boolean =
    this.isTrue || second.isTrue

@JvmName("and_nullableCSV_left_nullableCSV")
infix fun CSValue<Boolean>?.and(second: CSValue<Boolean>?): Boolean =
    this.isTrue && second.isTrue

@JvmName("or_nullableCSV_left_nullableCSV")
infix fun CSValue<Boolean>?.or(second: CSValue<Boolean>?): Boolean =
    this.isTrue || second.isTrue
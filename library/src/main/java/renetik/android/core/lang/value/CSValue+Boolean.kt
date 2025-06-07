@file:Suppress("NOTHING_TO_INLINE")

package renetik.android.core.lang.value

import renetik.android.core.kotlin.primitives.isTrue
import renetik.android.core.kotlin.then
import renetik.android.core.lang.Func

//─────────────────────────────────────────
// Properties: isTrue / isFalse
//─────────────────────────────────────────

inline val CSValue<Boolean>.isTrue: Boolean
    get() = value

@get:JvmName("CSValueBooleanOptionalIsTrue")
inline val CSValue<Boolean>?.isTrue: Boolean
    get() = this?.value == true

@get:JvmName("CSValueOptionalBooleanIsTrue")
inline val CSValue<Boolean?>.isTrue: Boolean
    get() = this.value == true

inline val CSValue<Boolean>.isFalse: Boolean
    get() = !value

//─────────────────────────────────────────
// Null-safe isTrue() for CSValue<Boolean?>
//─────────────────────────────────────────

@JvmName("isTrueBooleanNullable")
fun CSValue<Boolean?>.isTrue(): Boolean = value == true

//─────────────────────────────────────────
// Branching helpers
//─────────────────────────────────────────

inline fun <R> CSValue<Boolean>.ifTrue(function: () -> R): R? =
    if (isTrue) function() else null

inline fun <R> CSValue<Boolean>.ifFalse(function: () -> R): R? =
    if (isFalse) function() else null

inline fun CSValue<Boolean>.isTrue(function: Func) =
    then { if (isTrue) function() }

inline fun CSValue<Boolean>.isFalse(function: Func) =
    then { if (isFalse) function() }

//─────────────────────────────────────────
// Infix OR operators
//─────────────────────────────────────────

// Boolean · CSValue<Boolean>
@JvmName("or_CSValueOfBoolean")
infix fun Boolean.or(boolean: CSValue<Boolean>) =
    this || boolean.value

// Boolean · CSValue<Boolean?>
@JvmName("or_CSValueOfNullableBoolean")
infix fun Boolean.or(boolean: CSValue<Boolean?>) =
    this || boolean.isTrue

// Boolean · CSValue<Boolean>?
@JvmName("or_NullableCSValueOfBoolean")
infix fun Boolean.or(boolean: CSValue<Boolean>?) =
    this || boolean.isTrue

// Boolean? · CSValue<Boolean>
@JvmName("orBoxed_CSValueOfBoolean")
infix fun Boolean?.or(boolean: CSValue<Boolean>) =
    isTrue || boolean.value

// Boolean? · CSValue<Boolean?>
@JvmName("orBoxed_CSValueOfNullableBoolean")
infix fun Boolean?.or(boolean: CSValue<Boolean?>) =
    isTrue || boolean.isTrue

// Boolean? · CSValue<Boolean>?
@JvmName("orBoxed_NullableCSValueOfBoolean")
infix fun Boolean?.or(boolean: CSValue<Boolean>?) =
    isTrue || boolean.isTrue

// CSValue<Boolean> · Boolean
infix fun CSValue<Boolean>.or(second: Boolean): Boolean =
    value || second

// CSValue<Boolean> · CSValue<Boolean>
infix fun CSValue<Boolean>.or(second: CSValue<Boolean>): Boolean =
    value || second.value

// CSValue<Boolean?> · Boolean
@JvmName("or_CSVNullable_left_boolean")
infix fun CSValue<Boolean?>.or(second: Boolean): Boolean =
    this.isTrue || second

// CSValue<Boolean?> · CSValue<Boolean>
@JvmName("or_CSVNullable_left_CSVBoolean")
infix fun CSValue<Boolean?>.or(second: CSValue<Boolean>): Boolean =
    this.isTrue || second.value

// CSValue<Boolean?> · CSValue<Boolean?>
@JvmName("or_CSVNullable_left_CSVNullableBoolean")
infix fun CSValue<Boolean?>.or(second: CSValue<Boolean?>): Boolean =
    this.isTrue || second.isTrue

// CSValue<Boolean>? · Boolean
@JvmName("or_nullableCSV_left_boolean")
infix fun CSValue<Boolean>?.or(second: Boolean): Boolean =
    this.isTrue || second

// CSValue<Boolean>? · CSValue<Boolean>
@JvmName("or_nullableCSV_left_CSVBoolean")
infix fun CSValue<Boolean>?.or(second: CSValue<Boolean>): Boolean =
    this.isTrue || second.value

// CSValue<Boolean>? · CSValue<Boolean?>
@JvmName("or_nullableCSV_left_CSVNullableBoolean")
infix fun CSValue<Boolean>?.or(second: CSValue<Boolean?>): Boolean =
    this.isTrue || second.isTrue

// CSValue<Boolean>? · CSValue<Boolean>?
@JvmName("or_nullableCSV_left_nullableCSV")
infix fun CSValue<Boolean>?.or(second: CSValue<Boolean>?): Boolean =
    this.isTrue || second.isTrue

// CSValue<Boolean> · Boolean?
@JvmName("or_CSValueOfBoolean_nullableBoolean")
infix fun CSValue<Boolean>.or(second: Boolean?): Boolean =
    value || (second == true)

// CSValue<Boolean> · CSValue<Boolean?>
@JvmName("or_CSValueOfBoolean_CSVNullableBoolean")
infix fun CSValue<Boolean>.or(second: CSValue<Boolean?>): Boolean =
    value || second.isTrue

// CSValue<Boolean> · CSValue<Boolean>?
@JvmName("or_CSValueOfBoolean_nullableCSVBoolean")
infix fun CSValue<Boolean>.or(second: CSValue<Boolean>?): Boolean =
    value || second.isTrue

// CSValue<Boolean?> · Boolean?
@JvmName("or_CSVNullableBoolean_nullableBoolean")
infix fun CSValue<Boolean?>.or(second: Boolean?): Boolean =
    isTrue || (second == true)

// CSValue<Boolean?> · CSValue<Boolean>?
@JvmName("or_CSVNullableBoolean_nullableCSVBoolean")
infix fun CSValue<Boolean?>.or(second: CSValue<Boolean>?): Boolean =
    isTrue || second.isTrue

// CSValue<Boolean>? · Boolean?
@JvmName("or_nullableCSVBoolean_nullableBoolean")
infix fun CSValue<Boolean>?.or(second: Boolean?): Boolean =
    isTrue || (second == true)


//─────────────────────────────────────────
// Infix AND operators
//─────────────────────────────────────────

// Boolean · CSValue<Boolean>
@JvmName("and_CSValueOfBoolean")
infix fun Boolean.and(boolean: CSValue<Boolean>) =
    this && boolean.value

// Boolean · CSValue<Boolean?>
@JvmName("and_CSValueOfNullableBoolean")
infix fun Boolean.and(boolean: CSValue<Boolean?>) =
    this && boolean.isTrue

// Boolean · CSValue<Boolean>?
@JvmName("and_NullableCSValueOfBoolean")
infix fun Boolean.and(boolean: CSValue<Boolean>?) =
    this && boolean.isTrue

// Boolean? · CSValue<Boolean>
@JvmName("andBoxed_CSValueOfBoolean")
infix fun Boolean?.and(boolean: CSValue<Boolean>) =
    isTrue && boolean.value

// Boolean? · CSValue<Boolean?>
@JvmName("andBoxed_CSValueOfNullableBoolean")
infix fun Boolean?.and(boolean: CSValue<Boolean?>) =
    isTrue && boolean.isTrue

// Boolean? · CSValue<Boolean>?
@JvmName("andBoxed_NullableCSValueOfBoolean")
infix fun Boolean?.and(boolean: CSValue<Boolean>?) =
    isTrue && boolean.isTrue

// CSValue<Boolean> · Boolean
infix fun CSValue<Boolean>.and(second: Boolean): Boolean =
    value && second

// CSValue<Boolean?> · Boolean
@JvmName("and_CSVNullable_left_boolean")
infix fun CSValue<Boolean?>.and(second: Boolean): Boolean =
    this.isTrue && second

// CSValue<Boolean?> · CSValue<Boolean>
@JvmName("and_CSVNullable_left_CSVBoolean")
infix fun CSValue<Boolean?>.and(second: CSValue<Boolean>): Boolean =
    this.isTrue && second.value

// CSValue<Boolean?> · CSValue<Boolean?>
@JvmName("and_CSVNullable_left_CSVNullableBoolean")
infix fun CSValue<Boolean?>.and(second: CSValue<Boolean?>): Boolean =
    this.isTrue && second.isTrue

// CSValue<Boolean>? · Boolean
@JvmName("and_nullableCSV_left_boolean")
infix fun CSValue<Boolean>?.and(second: Boolean): Boolean =
    this.isTrue && second

// CSValue<Boolean>? · CSValue<Boolean>
@JvmName("and_nullableCSV_left_CSVBoolean")
infix fun CSValue<Boolean>?.and(second: CSValue<Boolean>): Boolean =
    this.isTrue && second.value

// CSValue<Boolean>? · CSValue<Boolean?>
@JvmName("and_nullableCSV_left_CSVNullableBoolean")
infix fun CSValue<Boolean>?.and(second: CSValue<Boolean?>): Boolean =
    this.isTrue && second.isTrue

// CSValue<Boolean>? · CSValue<Boolean>?
@JvmName("and_nullableCSV_left_nullableCSV")
infix fun CSValue<Boolean>?.and(second: CSValue<Boolean>?): Boolean =
    this.isTrue && second.isTrue

// CSValue<Boolean> · Boolean?
@JvmName("and_CSValueOfBoolean_nullableBoolean")
infix fun CSValue<Boolean>.and(second: Boolean?): Boolean =
    value && (second == true)

// CSValue<Boolean> · CSValue<Boolean>
@JvmName("and_CSValueOfBoolean_CSValueOfBoolean")
infix fun CSValue<Boolean>.and(second: CSValue<Boolean>): Boolean =
    value && second.value

// CSValue<Boolean> · CSValue<Boolean?>
@JvmName("and_CSValueOfBoolean_CSVNullableBoolean")
infix fun CSValue<Boolean>.and(second: CSValue<Boolean?>): Boolean =
    value && second.isTrue

// CSValue<Boolean> · CSValue<Boolean>?
@JvmName("and_CSValueOfBoolean_nullableCSVBoolean")
infix fun CSValue<Boolean>.and(second: CSValue<Boolean>?): Boolean =
    value && second.isTrue

// CSValue<Boolean?> · Boolean?
@JvmName("and_CSVNullableBoolean_nullableBoolean")
infix fun CSValue<Boolean?>.and(second: Boolean?): Boolean =
    isTrue && (second == true)

// CSValue<Boolean?> · CSValue<Boolean>?
@JvmName("and_CSVNullableBoolean_nullableCSVBoolean")
infix fun CSValue<Boolean?>.and(second: CSValue<Boolean>?): Boolean =
    isTrue && second.isTrue

// CSValue<Boolean>? · Boolean?
@JvmName("and_nullableCSVBoolean_nullableBoolean")
infix fun CSValue<Boolean>?.and(second: Boolean?): Boolean =
    isTrue && (second == true)

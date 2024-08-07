package renetik.android.core.lang.value

import renetik.android.core.kotlin.primitives.containsAll
import renetik.android.core.kotlin.then
import renetik.android.core.lang.Func
import renetik.android.core.lang.variable.CSVariable

infix fun Any.equals(other: CSValue<*>): Boolean = this == other.value
infix fun Any.equalsNot(other: CSValue<*>): Boolean = this != other.value
infix fun CSValue<*>.equals(other: Any): Boolean = value == other
infix fun CSValue<*>.equalsNot(other: Any): Boolean = value != other

inline val <T> CSValue<T?>.isNull get() = value == null
inline val <T> CSValue<T?>.notNull get() = value != null

inline val CSValue<Boolean>.isTrue get() = value

@get:JvmName("CSValueBooleanOptionalIsTrue")
inline val CSValue<Boolean>?.isTrue: Boolean get() = this?.value == true

inline val CSValue<Boolean>.isFalse get() = !value

inline fun <R> CSValue<Boolean>.ifTrue(function: () -> R): R? =
    if (isTrue) function() else null

inline fun <R> CSValue<Boolean>.ifFalse(function: () -> R): R? =
    if (isFalse) function() else null

@JvmName("isTrueBooleanNullable")
fun CSValue<Boolean?>.isTrue(): Boolean = value == true

inline fun CSValue<Boolean>.isTrue(function: Func) = then { if (isTrue) function() }
inline fun CSValue<Boolean>.isFalse(function: Func) = then { if (isFalse) function() }

infix fun CSValue<Boolean>.and(second: Boolean): Boolean = value && second
infix fun CSValue<Boolean>.or(second: Boolean): Boolean = value || second
infix fun Boolean.and(second: CSValue<Boolean>): Boolean = this && second.value
infix fun Boolean.or(second: CSValue<Boolean>): Boolean = this || second.value

inline val CSValue<Int>.number get() = value
inline val CSValue<Int>.next get() = value + 1
inline val CSValue<Int>.previous get() = value - 1

inline val CSValue<Float>.number get() = value
inline val CSValue<Double>.number get() = value

@get:JvmName("CSValueCharSequenceIsEmpty")
inline val CSValue<out CharSequence>.isEmpty get() = value.isEmpty()

fun CSValue<out CharSequence>.contains(
    value: String, ignoreCase: Boolean = false
) = this.value.contains(value, ignoreCase)

fun CSValue<out CharSequence>.contains(
    property: CSVariable<String>, ignoreCase: Boolean = false
) = this.contains(property.value, ignoreCase)

fun CSValue<out CharSequence>.containsAll(
    words: List<String>, ignoreCase: Boolean = false
) = value.containsAll(words, ignoreCase)
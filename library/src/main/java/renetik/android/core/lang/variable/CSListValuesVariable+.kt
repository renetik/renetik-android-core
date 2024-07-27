package renetik.android.core.lang.variable

val <T : Enum<*>> CSListValuesVariable<T>.isLast get() = values.lastIndex == value.ordinal

val <T : Enum<*>> CSListValuesVariable<T>.next: T get() = values[value.ordinal + 1]

fun <T : Enum<*>> CSListValuesVariable<T>.next() = value(next)

val <T : Enum<*>> CSListValuesVariable<T>.previous: T get() = values[value.ordinal - 1]

fun <T : Enum<*>> CSListValuesVariable<T>.previous() = value(previous)

fun <T> CSListValuesVariable<T>.value(index: Int) = value(values[index])
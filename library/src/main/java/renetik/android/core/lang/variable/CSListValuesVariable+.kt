package renetik.android.core.lang.variable

val <T : Enum<*>> CSListValuesVariable<T>.isLast get() = values.lastIndex == value.ordinal

fun <T : Enum<*>> CSListValuesVariable<T>.getNext(): T = values[value.ordinal + 1]

fun <T : Enum<*>> CSListValuesVariable<T>.getPrevious(): T = values[value.ordinal - 1]

fun <T> CSListValuesVariable<T>.value(index: Int) = value(values[index])
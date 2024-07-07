package renetik.android.core.lang.variable

@Deprecated("I don't like this", ReplaceWith("something else"))
val <T : Enum<*>> CSListValuesVariable<T>.isLast get() = values.lastIndex == value.ordinal

@Deprecated("I don't like this", ReplaceWith("something else"))
fun <T : Enum<*>> CSListValuesVariable<T>.next(): T = values[value.ordinal + 1]

@Deprecated("I don't like this", ReplaceWith("something else"))
fun <T : Enum<*>> CSListValuesVariable<T>.previous(): T = values[value.ordinal - 1]
//fun <T> CSListValuesVariable<T>.value(index: Int) = value(values[index])
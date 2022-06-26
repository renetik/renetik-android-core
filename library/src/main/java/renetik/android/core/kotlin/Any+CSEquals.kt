package renetik.android.core.kotlin

fun Any?.equalsAny(vararg items: Any?) = equalsAny(items.asIterable())
infix fun Any?.equalsAny(items: Iterable<Any?>): Boolean {
	for (item in items) if (this == item) return true; return false
}

fun Any?.equalsNone(vararg items: Any?) = equalsNone(items.asIterable())
infix fun Any?.equalsNone(items: Iterable<Any?>) = !equalsAny(items)

fun Any?.equalsAll(vararg items: Any?) = equalsAll(items.asIterable())
infix fun Any?.equalsAll(items: Iterable<Any?>): Boolean {
	for (item in items) if (this != item) return false; return true
}
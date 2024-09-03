package renetik.android.core.kotlin

fun Any?.equalsAny(vararg items: Any?): Boolean {
    for (item in items) if (this == item) return true
    return false
}

infix fun Any?.equalsAny(items: Iterable<Any?>): Boolean {
    for (item in items) if (this == item) return true
    return false
}

fun Any?.equalsNone(vararg items: Any?): Boolean {
    for (item in items) if (this == item) return false
    return true
}

infix fun Any?.equalsNone(items: Iterable<Any?>) = !equalsAny(items)

fun Any?.equalsAll(vararg items: Any?): Boolean {
    for (item in items) if (this != item) return false
    return true
}

infix fun Any?.equalsAll(items: Iterable<Any?>): Boolean {
    for (item in items) if (this != item) return false
    return true
}
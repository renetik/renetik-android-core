package renetik.android.core.kotlin

fun isAnyNotNull(vararg items: Any?) = !isAllNull(*items)

fun isAllNotNull(vararg items: Any?): Boolean {
    for (it in items) if (it == null) return false
    return true
}

fun isAnyNull(vararg items: Any?) = !isAllNotNull(*items)

fun isAllNull(vararg items: Any?): Boolean {
    for (it in items) if (it != null) return false
    return true
}
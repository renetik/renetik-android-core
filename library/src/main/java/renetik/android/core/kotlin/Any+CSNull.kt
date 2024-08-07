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

fun <T : Any, R> T?.ifNull(block: () -> R): R? = if (this == null) block() else null

fun <T : Any, R> T?.ifNotNull(block: (T) -> R): R? =
    if (this != null) block(this) else null


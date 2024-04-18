package renetik.android.core.kotlin

import renetik.android.core.lang.variable.CSVariable

fun isAnyNotNull(vararg items: Any?) = !isAllNull(*items)

fun isAllNotNull(vararg items: Any?): Boolean {
    for (it in items) if (it.isNull) return false
    return true
}

fun isAnyNull(vararg items: Any?) = !isAllNotNull(*items)

fun isAllNull(vararg items: Any?): Boolean {
    for (it in items) if (!it.isNull) return false
    return true
}

val Any?.isNull get() = this == null
val Any?.notNull get() = this != null

fun <T : Any> T?.onNull(block: () -> Unit) {
    if (this == null) block()
}

fun <T : Any> T?.onNotNull(block: (T) -> Unit) {
    if (this != null) block(this)
}

fun <T : Any, R> T?.isNull(block: () -> R): R? = if (this == null) block() else null
fun <T : Any, R> T?.isNotNull(block: (T) -> R): R? = if (this != null) block(this) else null

val CSVariable<out Any?>.isNull get() = value.isNull
val CSVariable<out Any?>.notNull get() = value.notNull
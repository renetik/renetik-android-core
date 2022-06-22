package renetik.android.core.kotlin

import renetik.android.core.lang.property.CSProperty

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

fun <T : Any, R> T?.notNull(block: (T) -> R): R? = if (this != null) block(this) else null

val <T : Any> T?.notNull get() = this != null

fun <T : Any, R> T?.isNull(block: () -> R): R? = if (this == null) block() else null

val Any?.isNull get() = this == null

//Has to be here otherwise calls go to Any?.isNull
val CSProperty<out Any?>.isNull get() = value == null
package renetik.android.core.kotlin

import renetik.android.core.lang.CSConditionalResult
import renetik.android.core.lang.Func
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

fun <T : Any, R> T?.isNull(block: () -> R): R? = if (this == null) block() else null

fun <T : Any> T?.ifNull(block: Func): CSConditionalResult {
    if (this == null) block()
    return CSConditionalResult(this != null)
}

fun <T : Any, R> T?.notNull(block: (T) -> R): R? = if (this != null) block(this) else null

fun <T : Any> T?.ifNotNull(block: Func): CSConditionalResult {
    if (this != null) block()
    return CSConditionalResult(this == null)
}

val CSVariable<out Any?>.isNull get() = value.isNull
val CSVariable<out Any?>.notNull get() = value.notNull
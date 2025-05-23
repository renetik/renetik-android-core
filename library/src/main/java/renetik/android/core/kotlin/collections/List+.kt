@file:Suppress("NOTHING_TO_INLINE")

package renetik.android.core.kotlin.collections

import renetik.android.core.lang.CSList

inline fun <T> List<T>.mutableAdd(element: T): MutableList<T> =
    mutable().apply { add(element) }

inline fun <T> List<T>.mutableAdd(index: Int, element: T): MutableList<T> =
    mutable().apply { add(index, element) }

inline fun <T> List<T>.clone(): List<T> = toList()

inline fun <T> List<T>.mutable(): MutableList<T> = toMutableList()

inline fun <T> List<T>.mutable(
    add: T? = null, remove: T? = null,
) = mutable().apply {
    add?.let { add(element = it) }
    remove?.let { remove(element = it) }
}

inline val <T> List<T>.first: T? get() = at(0)
inline val <T> List<T>.second: T? get() = at(1)
inline val <T> List<T>.third: T? get() = at(2)
inline val <T> List<T>.beforeLast: T? get() = at(lastIndex - 1)
inline val <T> List<T>.last: T? get() = at(lastIndex)

inline fun <T> List<T>.first(): T = this[0]
inline fun <T> List<T>.second(): T = this[1]
inline fun <T> List<T>.third(): T = this[2]
inline fun <T> List<T>.fourth(): T = this[3]
inline fun <T> List<T>.fifth(): T = this[4]
inline fun <T> List<T>.sixth(): T = this[5]
inline fun <T> List<T>.seventh(): T = this[6]

inline fun <T> List<T>.one() = this[0]
inline fun <T> List<T>.two() = this[1]
inline fun <T> List<T>.three() = this[2]
inline fun <T> List<T>.four() = this[3]
inline fun <T> List<T>.five() = this[4]
inline fun <T> List<T>.six() = this[5]
inline fun <T> List<T>.seven() = this[6]


inline fun <T> List<T>.at(index: Int): T? = if (index in indices) get(index) else null

@JvmName("containsAnyVararg")
inline fun <T> List<T>.containsAny(vararg items: T): Boolean =
    items.any { it in this }

inline infix fun <T> List<T>.containsAny(items: Array<out T>): Boolean =
    items.any { it in this }

inline infix fun <T> List<T>.containsAny(items: Iterable<T>): Boolean =
    items.any { it in this }

@JvmName("containsAllVararg")
inline fun <T> List<T>.containsAll(vararg items: T): Boolean =
    items.all { it in this }

inline infix fun <T> List<T>.containsAll(items: Array<T>) =
    items.all { it in this }

inline infix fun <T> List<T>.containsAll(items: Iterable<T>) =
    items.all { it in this }

@JvmName("containsNoneVararg")
inline fun <T> List<T>.containsNone(vararg items: T): Boolean =
    items.none { it in this }

inline infix fun <T> List<T>.containsNone(items: Array<T>) =
    items.none { it in this }

inline infix fun <T> List<T>.containsNone(items: Iterable<T>) =
    items.none { it in this }

inline fun <T> List<T>.beforeLast() = this[lastIndex - 1]

inline infix fun <T> List<T>.isLast(item: T): Boolean = last === item

inline infix fun <T> List<T>.isLastIndex(index: Int): Boolean = index == lastIndex

inline infix fun <T> List<T>.join(list: List<T>): List<T> =
    toMutableList().apply { addAll(list) }

fun <T> list(block: (MutableList<T>.() -> Unit)? = null): CSList<T> =
    CSList<T>().apply { block?.invoke(this) }

inline fun <T> list(size: Int): MutableList<T> = ArrayList(size)
inline fun <T> listOfNulls(size: Int) = list<T?>(size, null)
inline fun <T> list(size: Int, default: T) = MutableList(size) { default }
inline fun <T> list(size: Int, init: (index: Int) -> T) = MutableList(size, init)
inline fun <T> list(vararg items: T): MutableList<T> = mutableListOf(*items)
inline fun <T> list(items: Iterable<T>): MutableList<T> = list<T>().putAll(items)
inline fun <T> list(items: Collection<T>): MutableList<T> = CSList(items)

@JvmName("listItemsArray")
inline fun <T> list(items: Array<out T>): MutableList<T> = list<T>().putAll(*items)
inline fun <T> list(vararg items: Iterable<T>): MutableList<T> = list<T>().also {
    for (iterable in items) it.addAll(iterable)
}

inline fun <reified T> list(
    size: Int, create: (index: Int, previous: T?, size: Int) -> T,
): List<T> {
    var previous: T? = null
    return List(size) { index -> create(index, previous, size).apply { previous = this } }
}

inline fun <T, A, B> combine(
    arrayA: Array<A>, arrayB: Array<B>, createItem: (A, B) -> T,
) = combine(arrayA.asList(), arrayB.asList(), createItem)

inline fun <T, A, B> combine(
    collectionA: Collection<A>, collectionB: Collection<B>, createItem: (A, B) -> T,
) = list<T>(size = collectionA.size * collectionB.size).apply {
    for (a in collectionA) for (b in collectionB) add(createItem(a, b))
}

inline fun <T> List<T>.forEachReverse(action: (T) -> Unit) {
    var index = lastIndex
    while (index >= 0) {
        action(this[index])
        index--
    }
}

inline val <reified T> List<T>.doubled
    get() = List(size = size * 2) { index ->
        val valueIndex = if (index < size) index
        else index - size
        this[valueIndex]
    }

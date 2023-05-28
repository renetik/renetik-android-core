package renetik.android.core.kotlin.collections

import renetik.android.core.lang.CSList

fun <T> List<T>.clone() = toList()

fun <T> List<T>.mutable() = toMutableList()

fun <T> List<T>.mutable(
    add: T? = null, remove: T? = null,
) = mutable().apply {
    add?.let { add(element = it) }
    remove?.let { remove(element = it) }
}

val <T> List<T>.first get() = at(0)

val <T> List<T>.second get() = at(1)

val <T> List<T>.third get() = at(2)

val <T> List<T>.beforeLast get() = at(lastIndex - 1)

val <T> List<T>.last get() = at(lastIndex)

fun <T> List<T>.at(index: Int): T? = if (index in indices) get(index) else null

infix fun <T> List<T>.has(item: T): Boolean = contains(item)

infix fun <T> List<T>.containsAny(items: Array<out T>): Boolean {
    items.forEach { if (contains(it)) return true }
    return false
}

infix fun <T> List<T>.containsAny(items: Iterable<T>): Boolean {
    items.forEach { if (contains(it)) return true }
    return false
}

infix fun <T> List<T>.containsAll(items: Iterable<T>): Boolean {
    items.forEach { if (!contains(it)) return false }
    return true
}

infix fun <T> List<T>.containsNone(items: Iterable<T>) = !containsAny(items)

fun <T> List<T>.second() = this[1]

fun <T> List<T>.third() = this[2]

fun <T> List<T>.beforeLast() = this[lastIndex - 1]

infix fun <T> List<T>.isLast(item: T): Boolean = last === item

infix fun <T> List<T>.isLastIndex(index: Int): Boolean = index == lastIndex

infix fun <T> List<T>.join(list: List<T>): List<T> = toMutableList().apply { addAll(list) }

fun <T> list(block: (MutableList<T>.() -> Unit)? = null): CSList<T> =
    CSList<T>().apply { block?.invoke(this) }

fun <T> list(size: Int): MutableList<T> = ArrayList(size)
fun <T> listOfNulls(size: Int) = list<T?>(size, null)
fun <T> list(size: Int, default: T) = MutableList(size) { default }
fun <T> list(size: Int, init: (index: Int) -> T) = MutableList(size, init)
fun <T> list(vararg items: T): MutableList<T> = list<T>().putAll(*items)
fun <T> list(items: Iterable<T>): MutableList<T> = list<T>().putAll(items)
fun <T> list(items: Collection<T>): MutableList<T> = CSList(items)

@JvmName("listItemsArray")
fun <T> list(items: Array<out T>): MutableList<T> = list<T>().putAll(*items)
fun <T> list(vararg items: Iterable<T>): MutableList<T> = list<T>().also {
    for (iterable in items) it.addAll(iterable)
}

inline fun <reified T> list(
    size: Int, create: (index: Int, previous: T?, size: Int) -> T,
): List<T> {
    var previous: T? = null
    return List(size) { index -> create(index, previous, size).apply { previous = this } }
}

fun <T, A, B> combine(
    arrayA: Array<A>, arrayB: Array<B>, createItem: (A, B) -> T,
) = combine(arrayA.asList(), arrayB.asList(), createItem)

fun <T, A, B> combine(
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

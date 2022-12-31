package renetik.android.core.kotlin.collections

inline val Collection<*>.length get() = size

inline val Collection<*>.count get() = size

inline val Collection<*>.hasItems get() = isNotEmpty()

inline val Collection<*>.isEmpty get() = isEmpty()

inline val Collection<*>.lastIndex get() = size - 1

infix fun <T> Collection<T>.hasAll(items: List<T>): Boolean = containsAll(items)
infix fun <T> Collection<T>.hasAll(items: Array<out T>): Boolean = containsAll(items.asList())
infix fun <T> Collection<T>.hasAll(items: Iterable<T>): Boolean = containsAll(items.toList())
infix fun <T> Collection<T>.hasNot(element: T) = !contains(element)

fun <T> Collection<T>.hasNot(vararg elements: T): Boolean {
    for (element in elements) if (!hasNot(element)) return false
    return true
}

fun <T> Collection<T>.index(item: T): Int? =
    indexOf(item).let { if (it == -1) null else it }

fun <T> Collection<T>.firstIndex(predicate: (T) -> Boolean): Int? =
    indexOfFirst(predicate).let { if (it == -1) null else it }

fun <T> Collection<T>.lastIndex(predicate: (T) -> Boolean): Int? =
    indexOfLast(predicate).let { if (it == -1) null else it }

//inline fun <T> Iterable<T>.onEach(action: (T) -> Unit) = apply {
//    for (element in this) action(element)
//}


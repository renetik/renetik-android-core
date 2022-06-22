package renetik.android.core.kotlin.collections

fun <K, V> MutableMap<K, V>.add(key: K, value: V): V {
    put(key, value)
    return value
}

fun <K, V> MutableMap<K, V>.reload(map: MutableMap<K, V>) = apply {
    clear()
    putAll(map)
}
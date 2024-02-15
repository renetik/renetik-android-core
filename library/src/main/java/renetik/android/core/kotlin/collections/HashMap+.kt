package renetik.android.core.kotlin.collections

//fun <K, V> map(): MutableMap<K, V> = hashMapOf()
//fun <K, V> map(vararg pairs: Pair<K, V>) = mapOf(*pairs)

//fun map(vararg values: Any) = map<Any, Any>().apply {
//    var i = 0
//    while (i < values.size) {
//        put(values[i], values[i + 1])
//        i += 2
//    }
//
//}

//fun map(vararg values: String) = map<String, String>().apply {
//    var i = 0
//    while (i < values.size) {
//        put(values[i], values[i + 1])
//        i += 2
//    }
//}

fun <K, V> Map<K, V>.hasKey(key: K): Boolean = containsKey(key)
fun <K, V> Map<K, V>.hasValue(value: V): Boolean = containsValue(value)
fun <K, V> Map<K, V>.value(key: K): V? = get(key)
fun <K, V> MutableMap<K, V>.put(pair: Pair<K, V>) = apply {
    put(pair.first, pair.second)
}

fun <K, V> MutableMap<K, V>.reload(map: Map<K, V>) = apply {
    clear(); putAll(map)
}
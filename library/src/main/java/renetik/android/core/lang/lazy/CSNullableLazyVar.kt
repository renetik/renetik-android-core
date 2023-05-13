package renetik.android.core.lang.lazy

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> nullableLazyVar(initializer: () -> T) = CSNullableLazyVar(initializer)

class CSNullableLazyVar<T>(initializer: () -> T) : ReadWriteProperty<Any?, T> {
    private object initialValue

    var isSet = false
    private val lazyValue by lazy { initializer() }
    private var value: Any? = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        synchronized(this) {
            if (!isSet) return lazyValue
            @Suppress("UNCHECKED_CAST")
            return value as T
        }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        synchronized(this) {
            this.value = value
            isSet = true
        }
}


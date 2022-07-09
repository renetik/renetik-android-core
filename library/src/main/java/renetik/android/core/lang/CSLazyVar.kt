package renetik.android.core.lang

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> lazyVar(initializer: () -> T) = CSLazyVar(initializer)

class CSLazyVar<T>(initializer: () -> T) : ReadWriteProperty<Any?, T> {
    private object initialValue

    var isSet = false
    private val lazyValue by lazy { initializer() }
    private var value: Any? = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        synchronized(this) {
            if (!isSet) return lazyValue
            return value as T
        }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        synchronized(this) {
            this.value = value
            isSet = true
        }
}


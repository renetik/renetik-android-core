package renetik.android.core.lang.lazy

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class CSLazyNullableVar<T>(initializer: () -> T) : ReadWriteProperty<Any?, T> {
    private object initialValue

    private var isSet = false
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

    companion object {
        fun <T> lazyNullableVar(initializer: () -> T) = CSLazyNullableVar(initializer)
    }
}


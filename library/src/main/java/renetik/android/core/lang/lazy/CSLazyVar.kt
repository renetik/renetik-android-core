package renetik.android.core.lang.lazy

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun <T> lazyVar(initializer: () -> T) = CSLazyVar(initializer)

class CSLazyVar<T>(private val onLoad: () -> T) : ReadWriteProperty<Any?, T> {
    private var isInitialized = false
    private var value: T? = null

    override fun getValue(
        thisRef: Any?, property: KProperty<*>
    ): T = synchronized(this) {
        if (!isInitialized) {
            value = onLoad()
            isInitialized = true
        }
        return value!!
    }

    override fun setValue(
        thisRef: Any?, property: KProperty<*>, value: T
    ) = synchronized(this) {
        isInitialized = true
        this.value = value
    }
}
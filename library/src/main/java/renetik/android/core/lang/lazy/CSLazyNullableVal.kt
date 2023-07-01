package renetik.android.core.lang.lazy

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class CSLazyNullableVal<T>(private val onLoad: () -> T) : ReadOnlyProperty<Any?, T?> {

    @get:Synchronized
    var isInitialized = false
        private set

    private var value: T? = null

    fun reset() {
        value = null
        isInitialized = false
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T? = synchronized(this) {
        if (!isInitialized) {
            value = onLoad()
            isInitialized = true
        }
        return value
    }

    companion object {
        fun <T> lazyNullableVal(initializer: () -> T) = CSLazyVal(initializer)
    }
}
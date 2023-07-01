package renetik.android.core.lang.lazy

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class CSLazyVar<T>(private val onLoad: () -> T) : ReadWriteProperty<Any?, T> {

    @get:Synchronized
    var isInitialized = false
        private set

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

    companion object {
        fun <T> lazyVar(initializer: () -> T) = CSLazyVar(initializer)
    }
}
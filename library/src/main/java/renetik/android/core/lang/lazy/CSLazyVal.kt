package renetik.android.core.lang.lazy

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import renetik.android.core.lang.atomic.CSAtomic.Companion.atomic

class CSLazyVal<T>(
    private val onLoad: () -> T
) : ReadOnlyProperty<Any?, T>, CSLazyProperty<T> {

    override var isInitialized by atomic(false)
        private set

    override var value: T? = null
        private set

    override fun reset() = synchronized(this) {
        isInitialized = false
        value = null
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = synchronized(this) {
        if (!isInitialized) {
            value = onLoad()
            isInitialized = true
        }
        return value!!
    }

    companion object {
        fun <T> lazyVal(initializer: () -> T) = CSLazyVal(initializer)
    }
}
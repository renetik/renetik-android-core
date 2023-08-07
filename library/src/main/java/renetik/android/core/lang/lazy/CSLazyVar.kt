package renetik.android.core.lang.lazy

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import renetik.android.core.lang.atomic.CSAtomic.Companion.atomic

class CSLazyVar<T>(
    private val onLoad: () -> T
) : ReadWriteProperty<Any?, T>, CSLazyProperty<T> {

    override var isInitialized by atomic(false)
        private set

    override var value: T? = null

    override fun reset() = synchronized(this) {
        isInitialized = false
        value = null
    }

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
package renetik.android.core.lang.lazy

import renetik.android.core.lang.atomic.CSAtomicBoolean.Companion.atomic
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class CSLazyVar<T>(
    private val onLoad: () -> T
) : ReadWriteProperty<Any?, T>, CSLazyProperty {

    override var isInitialized by atomic(false)
        private set

    private var value: T? = null

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
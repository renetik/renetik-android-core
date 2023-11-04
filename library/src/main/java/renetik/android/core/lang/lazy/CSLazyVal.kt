package renetik.android.core.lang.lazy

import renetik.android.core.lang.atomic.CSAtomic.Companion.atomic
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class CSLazyVal<T>(
    private val onLoad: () -> T
) : ReadOnlyProperty<Any?, T>, CSLazyProperty<T> {

    private var _isInitialized by atomic(false)
    override fun isInitialized(): Boolean = _isInitialized

    override var value: T? = null
        private set

    override fun reset() = synchronized(this) {
        _isInitialized = false
        value = null
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = synchronized(this) {
        if (!_isInitialized) {
            value = onLoad()
            _isInitialized = true
        }
        return value!!
    }

    companion object {
        fun <T> lazyVal(initializer: () -> T) = CSLazyVal(initializer)
    }
}
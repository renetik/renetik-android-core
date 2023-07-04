package renetik.android.core.lang.lazy

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

fun <T> reloadLazyVar(initializer: () -> T) = CSReloadLazyVar(initializer)

class CSReloadLazyVar<T>(
    private val onLoad: () -> T
) : ReadWriteProperty<Any?, T> {

    private var value: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        synchronized(this) {
            if (value == null) value = onLoad()
            return value!!
        }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        synchronized(this) { this.value = value }

    fun reset() {
        value = null
    }

    companion object {
        fun reset(property: KProperty0<*>) {
            property.isAccessible = true
            (property.getDelegate() as CSReloadLazyVar<*>).reset()
        }
    }
}
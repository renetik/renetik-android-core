package renetik.android.core.lang.variable

import renetik.android.core.lang.ArgFunc
import kotlin.reflect.KProperty

class CSVariableImpl<T>(
    override var value: T,
    val onChange: ArgFunc<T>? = null
) : CSVariable<T> {

    fun apply() = apply { onChange?.invoke(value) }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        synchronized(this) { value }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        synchronized(this) { this.value = value }
        onChange?.invoke(value)
    }
}
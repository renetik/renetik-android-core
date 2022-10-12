package renetik.android.core.lang.variable

import renetik.android.core.lang.ArgFunc
import renetik.android.core.lang.value.CSValue
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface CSVariable<T> : CSValue<T>, ReadWriteProperty<Any?, T> {

    companion object {
        fun <T> variable(value: T, onChange: ArgFunc<T>? = null): CSVariable<T> =
            CSVariableImpl(value, onChange)

        fun <T> variableNull(value: T? = null, onChange: ArgFunc<T?>? = null): CSVariable<T?> =
            variable(value, onChange)

        fun <T> variable(onChange: ArgFunc<T>? = null): CSVariable<T> =
            CSLateVariableImpl(onChange)
    }

    override var value: T

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        synchronized(this) { value }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        synchronized(this) { this.value = value }
}
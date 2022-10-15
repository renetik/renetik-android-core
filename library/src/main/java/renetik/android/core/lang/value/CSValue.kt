package renetik.android.core.lang.value

import renetik.android.core.lang.variable.CSVariable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

interface CSValue<T> : ReadOnlyProperty<Any?, T> {
    companion object {
        fun <T> value(value: T) = object : CSSynchronizedValue<T> {
            override val value: T = value
        }
    }

    val value: T

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        synchronized(this) { value }
}

interface CSSynchronizedValue<T> : CSValue<T>
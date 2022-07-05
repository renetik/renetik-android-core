package renetik.android.core.lang

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

interface CSValue<T> : ReadOnlyProperty<Any?, T> {
    val value: T

    companion object {
        fun <T> value(value: T) = object : CSSynchronizedValue<T> {
            override val value: T = value
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        synchronized(this) { value }
}

interface CSSynchronizedValue<T> : CSValue<T>
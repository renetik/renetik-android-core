package renetik.android.core.lang.property

import renetik.android.core.lang.CSSynchronizedValue
import renetik.android.core.lang.CSValue
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface CSVariable<T> : CSValue<T>, ReadWriteProperty<Any?, T> {

    companion object {
        fun <T> variable(value: T) = object : CSVariable<T> {
            override var value: T = value
        }
    }

    override var value: T

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        synchronized(this) { value }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        synchronized(this) { this.value = value }
}
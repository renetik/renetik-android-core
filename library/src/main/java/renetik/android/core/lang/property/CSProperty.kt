package renetik.android.core.lang.property

import renetik.android.core.lang.CSValue
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface CSProperty<T> : CSValue<T>, ReadWriteProperty<Any?, T> {
    override var value: T

    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
        synchronized(this) { value }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) =
        synchronized(this) { this.value = value }
}
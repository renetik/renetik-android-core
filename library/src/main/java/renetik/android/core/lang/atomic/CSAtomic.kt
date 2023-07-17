package renetik.android.core.lang.atomic

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class CSAtomic(@Volatile private var value: Any) : ReadWriteProperty<Any?, Any> {

    override fun getValue(
        thisRef: Any?, property: KProperty<*>
    ): Any = value

    override fun setValue(
        thisRef: Any?, property: KProperty<*>, value: Any
    ) {
        this.value = value
    }

    companion object {
        fun atomic(value: Int) = CSAtomic(value)
    }
}
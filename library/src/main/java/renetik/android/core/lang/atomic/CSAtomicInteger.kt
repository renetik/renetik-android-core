package renetik.android.core.lang.atomic

import java.util.concurrent.atomic.AtomicInteger
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class CSAtomicInteger(value: Int) : ReadWriteProperty<Any?, Int> {
    private val value = AtomicInteger(value)

    override fun getValue(
        thisRef: Any?, property: KProperty<*>
    ): Int = value.get()

    override fun setValue(
        thisRef: Any?, property: KProperty<*>, value: Int
    ) = this.value.set(value)

    companion object {
        fun atomic(value: Int) = CSAtomicInteger(value)
    }
}
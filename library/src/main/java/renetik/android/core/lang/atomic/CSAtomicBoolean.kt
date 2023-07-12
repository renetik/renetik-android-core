package renetik.android.core.lang.atomic

import java.util.concurrent.atomic.AtomicBoolean
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class CSAtomicBoolean(value: Boolean) : ReadWriteProperty<Any?, Boolean> {
    private val value = AtomicBoolean(value)

    override fun getValue(
        thisRef: Any?, property: KProperty<*>
    ): Boolean = value.get()

    override fun setValue(
        thisRef: Any?, property: KProperty<*>, value: Boolean
    ) = this.value.set(value)

    companion object {
        fun atomic(value: Boolean) = CSAtomicBoolean(value)
    }
}


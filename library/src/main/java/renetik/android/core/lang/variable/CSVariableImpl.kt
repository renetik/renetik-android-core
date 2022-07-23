package renetik.android.core.lang.variable

import renetik.android.core.lang.ArgFunc

class CSVariableImpl<T>(value: T, val onChange: ArgFunc<T>? = null) : CSVariable<T> {
    override var value: T = value
        set(value) {
            field = value
            onChange?.invoke(value)
        }

    fun apply() = apply {
        onChange?.invoke(value)
    }
}
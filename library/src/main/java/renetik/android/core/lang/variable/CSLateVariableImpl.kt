package renetik.android.core.lang.variable

import renetik.android.core.lang.ArgFunc

class CSLateVariableImpl<T>(
    val onChange: ArgFunc<T>? = null) : CSVariable<T> {
    private var privateValue: T? = null

    override var value: T
        get() = privateValue!!
        set(value) {
            privateValue = value
            onChange?.invoke(value)
        }

    fun apply() = apply {
        onChange?.invoke(value)
    }
}
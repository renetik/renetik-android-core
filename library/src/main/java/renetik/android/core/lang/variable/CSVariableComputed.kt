package renetik.android.core.lang.variable

import renetik.android.core.lang.Void

class CSVariableComputed<T>(
    val from: () -> T, val to: (T) -> Unit) : CSVariable<T> {
    override var value: T
        get() = from()
        set(value) = to(value)
}
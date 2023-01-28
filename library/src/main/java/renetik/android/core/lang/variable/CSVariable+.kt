package renetik.android.core.lang.variable

import renetik.android.core.kotlin.asString

fun <T> CSVariable<T>.value(value: T) = apply { this.value = value }

inline var CSVariable<String?>.string: String
    get() = value.asString
    set(newValue) {
        value = newValue
    }

fun CSVariable<Double>.value(value: Int) = apply { this.value = value.toDouble() }

fun CSVariable<Float>.value(value: Number) = apply { this.value = value.toFloat() }


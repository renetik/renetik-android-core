package renetik.android.core.lang.variable

fun <T : CSVariable<Int>> T.increment(): T = apply { value++ }

fun <T : CSVariable<Int>> T.decrement(): T = apply { value-- }

operator fun CSVariable<Int>.minusAssign(step: Int) {
    value -= step
}

operator fun CSVariable<Int>.plusAssign(step: Int) {
    value += step
}
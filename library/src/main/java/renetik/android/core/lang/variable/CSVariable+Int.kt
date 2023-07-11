package renetik.android.core.lang.variable

fun <T : CSVariable<Int>> T.increment(): T = apply { value++ }

fun <T : CSVariable<Int>> T.decrement(): T = apply { value-- }

operator fun CSVariable<Int>.minusAssign(value: Int) {
    this.value -= value
}

operator fun CSVariable<Int>.plusAssign(value: Int) {
    this.value += value
}
package renetik.android.core.lang.variable

fun CSVariable<Boolean>.setTrue() = apply { this.value = true }
fun CSVariable<Boolean>.setFalse() = apply { this.value = false }
fun CSVariable<Boolean>.toggle() = apply { value = !value }
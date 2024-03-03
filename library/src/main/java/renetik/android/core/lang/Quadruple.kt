package renetik.android.core.lang

import java.io.Serializable

data class Quadruple<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D
) : Serializable {
    override fun toString(): String = "($first, $second, $third, $fourth)"
}

infix fun <A, B, C, D> Triple<A, B, C>.to(that: D): Quadruple<A, B, C, D> =
    Quadruple(first, second, third, that)
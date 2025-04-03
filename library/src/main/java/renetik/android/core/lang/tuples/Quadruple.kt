package renetik.android.core.lang.tuples

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

data class Quintuple<out A, out B, out C, out D, out E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
) : Serializable {
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth)"
}

infix fun <A, B, C, D, E> Quadruple<A, B, C, D>.to(that: E): Quintuple<A, B, C, D, E> =
    Quintuple(first, second, third, fourth, that)


data class Sixtuple<out A, out B, out C, out D, out E, out F>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F
) : Serializable {
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth)"
}

infix fun <A, B, C, D, E, F> Quintuple<A, B, C, D, E>.to(that: F): Sixtuple<A, B, C, D, E, F> =
    Sixtuple(first, second, third, fourth, fifth, that)

data class Seventuple<out A, out B, out C, out D, out E, out F, out G>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
    val sixth: F,
    val seventh: G
) : Serializable {
    override fun toString(): String = "($first, $second, $third, $fourth, $fifth, $sixth, $seventh)"
}

infix fun <A, B, C, D, E, F, G> Sixtuple<A, B, C, D, E, F>.to(that: G)
        : Seventuple<A, B, C, D, E, F, G> =
    Seventuple(first, second, third, fourth, fifth, sixth, that)

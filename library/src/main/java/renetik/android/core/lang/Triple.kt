package renetik.android.core.lang

infix fun <A, B, C> Pair<A, B>.to(that: C): Triple<A, B, C> = Triple(first, second, that)

fun test() {
    val something = 45 to "Apple" to 57f
    val (number: Int, string: String, float: Float) = something
}
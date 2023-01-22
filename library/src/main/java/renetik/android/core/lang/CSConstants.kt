@file:Suppress("ObjectPropertyName")

package renetik.android.core.lang

object CSLang {
    val EmptyFunc = {}
}

object CSStringConstants {
    const val Empty = ""
    const val Ellipsize = "..."
    const val Comma = ","
    const val Semicolon = ";"
    const val NewLine = "\n"
    const val Space = " "
    const val `$` = "$"
}

object CSDataConstants {
    const val KB = 1024
    const val MB = 1024 * KB
    const val GB = 1024 * MB
}

object CSComparisonConstants {
    const val Ascending = -1
    const val Equal = 0
    const val Descending = 1
}

object CSTimeConstants {
    const val QuarterSecond = 250
    const val HalfSecond = 500
    const val Second = 1000
    const val SecondLong = Second.toLong()
    const val Minute = 60 * Second
    const val HalfMinute = Minute / 2
    const val Hour = 60 * Minute
    const val Day = 24 * Hour
    const val MilliToNanoSecondMultiplier = 1000000
    const val SecondNano = Second * MilliToNanoSecondMultiplier
    fun Int.toNanosecond() = this * MilliToNanoSecondMultiplier
}
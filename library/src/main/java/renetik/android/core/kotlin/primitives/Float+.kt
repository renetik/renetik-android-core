package renetik.android.core.kotlin.primitives

import renetik.android.core.extensions.content.dpToPixelF
import renetik.android.core.lang.CSEnvironment.app
import java.math.RoundingMode
import java.math.RoundingMode.CEILING
import java.math.RoundingMode.UP
import java.text.DecimalFormat
import java.util.Locale
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.roundToLong

val Float.Companion.Empty get() = MAX_VALUE
val Float.isEmpty get() = this == Float.Empty
val Float.isSet get() = !this.isEmpty
fun Float.ifEmpty(function: (Float) -> Unit) = apply {
    if (this.isEmpty) function(this)
}

fun Float.ifSet(function: (Float) -> Unit) = apply {
    if (this.isSet) function(this)
}

fun Float.roundToStep(step: Float): Float = (this / step).roundToLong() * step
fun Float.roundToStep(step: Double): Double = (this / step).roundToLong() * step
fun Float.roundToStep(step: Int): Int = (this / step.toFloat()).toInt() * step

fun Float.roundToDecimalPlaces(decimalPlaces: Int): Float {
    val factor = 10.0.pow(decimalPlaces.toDouble())
    return (this * factor).roundToInt() / factor.toFloat()
}

fun Float.roundToDecimal(
    decimalPlaces: Int, mode: RoundingMode = UP,
): Float = formatRoundDecimal(
    "#." + Array(decimalPlaces) { "#" }.joinToString(separator = ""), mode
).toFloat()

fun Float.formatDecimal(n: Int): String {
//    val prefix = if (this < 0) "-" else ""
    return "%.${n}f".format(Locale.ENGLISH, this)
}

fun Float.removeToDecimal(n: Int): Float {
    return formatDecimal(n).toFloat()
}

fun Float.formatRoundDecimal(
    format: String = "#.##",
    mode: RoundingMode = CEILING,
): String = DecimalFormat(format).apply { roundingMode = mode }.format(this)

val Float.rest: Float
    get() = toString().let {
        if ("." in it) {
            val value = it.substringAfter(".")
            value.toInt() / value.length.toFloat()
        } else 0f
    }

fun Float.rest(value: Int): Float =
    if (value > 0) this % value else this

fun Float.min(minimum: Float) = if (this > minimum) this else minimum
fun Float.max(maximum: Float) = if (this < maximum) this else maximum

inline val Float.dp: Float get() = app.dpToPixelF(this)
inline val Float.dpf: Float get() = app.dpToPixelF(this)

fun Float.percentOf(size: Float): Float = (this * size / 100.0).toFloat()
fun Float.percentOf(size: Int): Float = percentOf(size.toFloat())
fun Float.percentOf(size: Long): Float = percentOf(size.toFloat())
fun Float.percentOfInt(size: Float): Int = percentOf(size).toInt()
fun Float.percentOfInt(size: Int): Int = percentOf(size.toFloat()).toInt()
fun Float.toPercentOf(total: Float): Float = (this / total * 100).coerceIn(0f, 100f)
fun Float.toPercentOf(total: Int): Float = toPercentOf(total.toFloat())



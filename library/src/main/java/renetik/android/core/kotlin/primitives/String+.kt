package renetik.android.core.kotlin.primitives

import renetik.android.core.kotlin.notNull
import renetik.android.core.kotlin.text.StringBuilder
import renetik.android.core.kotlin.text.add
import renetik.android.core.kotlin.text.deleteLast
import renetik.android.core.kotlin.text.reload
import renetik.android.core.lang.CSStringConstants
import renetik.android.core.lang.catchWarnReturn
import renetik.android.core.lang.catchWarnReturnNull
import java.nio.charset.StandardCharsets
import java.text.Normalizer
import java.util.*

inline val String.Companion.NewLine get() = CSStringConstants.NewLine
inline val String.Companion.Comma get() = CSStringConstants.Comma
inline val String.Companion.Semicolon get() = CSStringConstants.Semicolon
inline val String.Companion.Empty get() = CSStringConstants.Empty
inline val String.Companion.Space get() = CSStringConstants.Space

fun String.Companion.random(length: Int): String {
    val random = Random()
    val letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    val text = CharArray(length)
    for (i in 0 until length)
        text[i] = letters[random.nextInt(letters.length)]
    return String(text)
}

fun String.Companion.contains(string1: String?, string2: String?, ignoreCase: Boolean = false) =
    if (string1 == null || string2 == null) false
    else string1.contains(string2, ignoreCase)

fun String.Companion.range(start: Int, endInclusive: Int): List<String> =
    IntRange(start, endInclusive).map { "$it" }

inline val String?.isEmpty get() = this?.trim()?.isEmpty() ?: true
inline val String?.isSet get() = !this.isEmpty
inline val String?.setOrNull get() = if (isSet) this else null
inline fun String.ifEmpty(function: (String) -> Unit) = apply {
    if (this.isEmpty) function(this)
}

inline fun String.ifSet(function: (String) -> Unit) = apply {
    if (this.isSet) function(this)
}


fun String.asLong() = catchWarnReturnNull<Long, NumberFormatException> { toLong() }
fun String.asFloat() = catchWarnReturnNull<Float, NumberFormatException> { toFloat() }
fun String.asDouble() = catchWarnReturnNull<Double, NumberFormatException> { toDouble() }
fun String.asInt() = catchWarnReturnNull<Int, NumberFormatException> { toInt() }
fun String.asDouble(default: Double) =
    catchWarnReturn<Double, NumberFormatException>(default) { toDouble() }

fun String.asLong(default: Long) =
    catchWarnReturn<Long, NumberFormatException>(default) { toLong() }

fun String.asFloat(default: Float) =
    catchWarnReturn<Float, NumberFormatException>(default) { toFloat() }

fun String.asInt(default: Int) =
    catchWarnReturn<Int, NumberFormatException>(default) { toInt() }

fun String.trimNewLines() = replace(String.NewLine, String.Empty)

fun String.remove(toRemove: String) = replace(toRemove, "")

fun String.replace(strings: Array<String>, replacement: String): String {
    val builder = StringBuilder(this)
    strings.forEach { builder.reload(builder.toString().replace(it, replacement)) }
    return builder.toString()
}

fun String.leaveEndOfLength(length: Int): String {
    if (this.length > length) {
        val toCut = this.length - length
        return substring(toCut)
    }
    return this
}

fun String.separateToString(vararg items: Any?): String {
    val text = StringBuilder()
    for (value in items) value.notNull { text.add(it).add(this) }
    if (!text.isEmpty) text.deleteLast(this.length)
    return text.toString()
}

val String.lowerCased: String get() = lowercase(Locale.ROOT)
val String.upperCased: String get() = uppercase(Locale.ROOT)
val String.noAccents: String get() = removeAccents()

private val nonSpacingCharactersRegex = "\\p{Mn}+".toRegex()
// some used this in remove accents code instead
// private val combiningdiacriticalMarksRegex = "\\p{Mn}+".toRegex()

fun String.removeAccents(): String {
    return Normalizer.normalize(this, Normalizer.Form.NFD)
        .replace(nonSpacingCharactersRegex, "")
}

fun String.toMaxBytesSize(length: Int): String {
    val nameBytes = toByteArray(StandardCharsets.UTF_8)
    return if (nameBytes.size > length) {
        val bytes = ByteArray(length)
        System.arraycopy(nameBytes, 0, bytes, 0, length)
        String(bytes, StandardCharsets.UTF_8)
    } else this
}

fun String.maxLengthOf(count: Int) = if (count > count) substring(0 until 10) else this

fun String.containsAll(words: List<String>, ignoreCase: Boolean = false): Boolean {
    for (word in words) if (!contains(word, ignoreCase)) return false
    return true
}

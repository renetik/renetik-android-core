package renetik.android.core.java.util

import android.content.Context
import android.text.format.DateFormat.getDateFormat
import android.text.format.DateFormat.getTimeFormat
import renetik.android.core.lang.catchAllWarn
import renetik.android.core.lang.catchError
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

val currentTime: Long get() = Date().time

fun dateFromString(format: String, string: String) = catchError<ParseException> {
    SimpleDateFormat(format, Locale.US).parse("" + string)
}

fun Date.format(dateStyle: Int = DateFormat.MEDIUM, timeStyle: Int = DateFormat.MEDIUM): String =
    DateFormat.getDateTimeInstance(dateStyle, timeStyle).format(this)

fun Date.formatDate(style: Int = DateFormat.MEDIUM): String =
    DateFormat.getDateInstance(style).format(this)

fun Date.formatTime(style: Int = DateFormat.MEDIUM): String =
    DateFormat.getTimeInstance(style).format(this)

fun Date.format(format: String): String = SimpleDateFormat(format, Locale.US).format(this)

val now get() = Date()

fun Date.modify(
    years: Int = 0, hours: Int = 0,
    minutes: Int = 0, seconds: Int = 0, millis: Int = 0
): Date {
    val instance = Calendar.getInstance()
    instance.time = this
    instance.add(Calendar.YEAR, years)
    instance.add(Calendar.HOUR, hours)
    instance.add(Calendar.MINUTE, minutes)
    instance.add(Calendar.SECOND, seconds)
    instance.add(Calendar.MILLISECOND, millis)
    return instance.time
}

fun Date.createDatedDirName() = format("yyyy-MM-dd_HH-mm-ss")

fun Date.formatToISO8601(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US)
    dateFormat.timeZone = TimeZone.getTimeZone("CET")
    return dateFormat.format(this)
}

fun Context.parseTimeFormat(text: String) = catchAllWarn { getTimeFormat(this).parse(text) }
fun Context.parseDateFormat(text: String) = catchAllWarn { getDateFormat(this).parse(text) }
fun Context.formatDate(date: Date): String = getDateFormat(this).format(date)
fun Context.formatTime(date: Date): String = getTimeFormat(this).format(date)
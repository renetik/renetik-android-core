package renetik.android.core.java.util

import java.util.Calendar
import java.util.Calendar.MILLISECOND
import java.util.Date
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

operator fun Date.minus(date: Date): Duration =
    (time - date.time).toDuration(DurationUnit.MILLISECONDS)

operator fun Date.minus(duration: Duration): Date =
    add(-duration.inWholeMilliseconds.toInt())

operator fun Date.plus(duration: Duration): Date =
    add(duration.inWholeMilliseconds.toInt())

private fun Date.add(millis: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(MILLISECOND, millis)
    return calendar.time
}
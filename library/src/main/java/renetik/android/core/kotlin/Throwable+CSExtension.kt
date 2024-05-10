package renetik.android.core.kotlin

import java.io.PrintWriter
import java.io.StringWriter

val Throwable.rootCauseMessage get() = rootCause?.message

val Throwable.rootCause: Throwable?
    get() {
        var throwable: Throwable? = this
        val list = ArrayList<Throwable>()
        while (throwable != null && !list.contains(throwable)) {
            list.add(throwable)
            throwable = throwable.cause
        }
        return if (list.size < 2) null else list[list.size - 1]
    }

val Throwable?.asTraceString: String
    get() {
        if (this == null) return ""
        val stringWriter = StringWriter()
        val printWriter = PrintWriter(stringWriter)
        printStackTrace(printWriter)
        printWriter.flush()
        return stringWriter.toString()
    }

fun Throwable.toShortString(skip: Int = 0, length: Int = 5): String {
    val stringWriter = StringWriter()
    val printWriter = PrintWriter(stringWriter)
    printStackTrace(printWriter)
    printWriter.flush()
    val stackTrace = stringWriter.toString()
    val lines = stackTrace.split(System.lineSeparator())
    val startIndex = minOf(skip, lines.size)
    val endIndex = minOf(startIndex + length, lines.size)
    return lines.subList(startIndex, endIndex).joinToString(System.lineSeparator())
}
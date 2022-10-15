package renetik.android.core.logging

import android.content.Context
import renetik.android.core.extensions.content.toast
import renetik.android.core.kotlin.CSUnexpectedException
import renetik.android.core.kotlin.primitives.leaveEndOfLength
import renetik.android.core.kotlin.then
import renetik.android.core.lang.CSStringConstants.NewLine
import renetik.android.core.logging.CSLogLevel.*
import renetik.android.core.logging.CSLogMessage.Companion.Empty
import renetik.android.core.logging.CSLogMessage.Companion.message
import java.lang.System.currentTimeMillis
import java.lang.Thread.currentThread
import java.text.DateFormat.getDateTimeInstance

object CSLog {
    var logger: CSLogger = CSPrintLogger()

    fun init(logger: CSLogger) {
        this.logger = logger
    }

    fun log(level: CSLogLevel) = then { logImpl(level) { message("") } }
    fun log(level: CSLogLevel, function: () -> CSLogMessage) =
        then { logImpl(level, function) }

    fun logDebug() = then { logImpl(Debug) { message("") } }
    fun logDebug(function: () -> CSLogMessage) = then { logImpl(Debug, function) }

    fun logInfo() = then { logImpl(Info) { message("") } }
    fun logInfo(function: () -> CSLogMessage) = then { logImpl(Info, function) }

    fun logWarn() = then { logImpl(Warn) { message("") } }
    fun logWarn(function: () -> CSLogMessage) = then { logImpl(Warn, function) }

    fun logError() = then { logImpl(Error) { message("") } }
    fun logError(function: () -> CSLogMessage) = then { logImpl(Error, function) }

    fun Context.logDebugToast() = toast(Debug, logImpl(Debug) { message("") })
    fun Context.logDebugToast(function: () -> CSLogMessage) =
        toast(Debug, logImpl(Debug, function))

    fun Context.logInfoToast() = toast(Info, logImpl(Info) { message("") })
    fun Context.logInfoToast(function: () -> CSLogMessage) =
        toast(Info, logImpl(Info, function))

    fun Context.logWarnToast() = toast(Warn, logImpl(Warn) { message("") })
    fun Context.logWarnToast(function: () -> CSLogMessage) =
        toast(Warn, logImpl(Warn, function))

    fun Context.logErrorToast() = toast(Error, logImpl(Error) { message("") })
    fun Context.logErrorToast(function: () -> CSLogMessage) =
        toast(Error, logImpl(Error, function))

    private fun logImpl(level: CSLogLevel, message: () -> CSLogMessage = { Empty }): Array<Any?>? {
        if (logger.isEnabled(level)) message().let {
            val text = createMessage(it.message)
            when (level) {
                Debug -> logger.debug(it.throwable, *text)
                Info -> logger.info(it.throwable, *text)
                Warn -> logger.warn(it.throwable, *text)
                Error -> logger.error(it.throwable, *text)
                else -> CSUnexpectedException.unexpected()
            }
            return text
        }
        return null
    }

    private fun Context.toast(level: CSLogLevel, text: Array<Any?>?) {
        if (logger.isEnabled(level)) toast("${text!![1]}".leaveEndOfLength(100)
            .chunked(50).joinToString { "$it$NewLine" })
    }

    private val timeFormat by lazy { getDateTimeInstance() }

    fun createMessage(message: Any?) = Array(3) {
        when (it) {
            0 -> timeFormat.format(currentTimeMillis())
            1 -> getTraceLine()
            else -> message
        }
    }

    private fun getTraceLine(): String {
        val stackTrace = currentThread().stackTrace
        val index = stackTrace.indexOfFirst { it.methodName == "logImpl" } + 2
        return stackTrace[index].let { element ->
            "${element.className}$${element.methodName}(${element.fileName}:${element.lineNumber})"
        }
    }
}
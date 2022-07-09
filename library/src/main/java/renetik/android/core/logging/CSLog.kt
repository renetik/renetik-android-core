package renetik.android.core.logging

import renetik.android.core.extensions.content.CSToast.toast
import renetik.android.core.kotlin.primitives.separateToString
import java.lang.System.currentTimeMillis
import java.lang.Thread.currentThread
import java.text.DateFormat.getDateTimeInstance

object CSLog {
    private const val NoMessage = "No Message"

    var logger: CSLogger = CSPrintLogger()

    fun init(logger: CSLogger) {
        this.logger = logger
    }

    fun logDebug(message: (() -> Any)? = null) {
        if (logger.isDebug) logger.debug(*createDebugMessage("logDebug",
            message?.invoke() ?: NoMessage))
    }

    fun logDebug(e: Throwable) = logger.debug(e)

    fun logWarn(vararg values: Any?) =
        logger.warn(*createMessage("logWarn", values))

    fun logWarn(e: Throwable, vararg values: Any?) =
        logger.warn(e, *createMessage("logWarn", values))

    fun logError(vararg values: Any?) =
        logger.error(*createMessage("logError", values))

    fun logError(e: Throwable, vararg values: Any?) =
        logger.error(e, *createMessage("logError", values))

    fun logInfo(vararg values: Any?) =
        logger.info(*createMessage("logInfo", values))

    fun logInfoToast(vararg values: Any?) {
        logger.info(*createMessage("logWarnToast", values))
        toast(" ".separateToString(*values))
    }

    fun logWarnToast(vararg values: Any?) {
        logger.warn(*createMessage("logWarnToast", values))
        toast(" ".separateToString(*values))
    }

    fun logErrorToast(vararg values: Any?) {
        logger.error(*createMessage("logErrorToast", values))
        toast(" ".separateToString(*values))
    }

    private fun createMessage(logFunctionName: String,
                              values: Array<out Any?>) = Array(values.size + 2) {
        when (it) {
            0 -> time
            1 -> getTraceLine(logFunctionName)
            else -> values[it - 2]
        }
    }

    private val timeFormat by lazy { getDateTimeInstance() }

    private fun getTraceLine(logFunctionName: String): String {
        val index = currentThread().stackTrace.indexOfFirst { it.methodName == logFunctionName } + 1
        return currentThread().stackTrace[index].let { element ->
            "${element.className}$${element.methodName}(${element.fileName}:${element.lineNumber})"
        }
    }


    private val time get() = timeFormat.format(currentTimeMillis())

    private fun createDebugMessage(
        logFunctionName: String, values: Array<out Any?>) = Array(values.size + 2) {
        when (it) {
            0 -> time
            1 -> getTraceLine(logFunctionName)
            else -> values[it - 2]
        }
    }

    private fun createDebugMessage(
        logFunctionName: String, message: Any?) = Array(3) {
        when (it) {
            0 -> time
            1 -> getTraceLine(logFunctionName)
            else -> message
        }
    }
}
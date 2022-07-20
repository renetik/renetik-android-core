package renetik.android.core.logging

import renetik.android.core.BuildConfig.LIBRARY_PACKAGE_NAME
import renetik.android.core.kotlin.asTraceString
import renetik.android.core.kotlin.primitives.Space
import renetik.android.core.kotlin.text.add
import renetik.android.core.kotlin.text.addSpace
import renetik.android.core.kotlin.text.deleteLast
import renetik.android.core.lang.void
import renetik.android.core.logging.CSLogLevel.*

class CSPrintLogger(
    val name: String = LIBRARY_PACKAGE_NAME,
    override val level: CSLogLevel = Debug,
    private val listener: ((event: CSLogLevel, message: String) -> void)? = null)
    : CSLogger {

    override fun debug(e: Throwable?, vararg values: Any?) {
        val message = createMessage(*values)
        println("$Debug: $name: $message $e ${e?.asTraceString ?: ""}")
        listener?.invoke(Debug, message.addSpace().add(e.asTraceString).toString())
    }

    override fun info(e: Throwable?, vararg values: Any?) {
        val message = createMessage(*values)
        println("$Info: $name: $message $e ${e?.asTraceString ?: ""}")
        listener?.invoke(Info, message.addSpace().add(e.asTraceString).toString())
    }

    override fun warn(e: Throwable?, vararg values: Any?) {
        val message = createMessage(*values)
        println("$Warn: $name: $message $e ${e?.asTraceString ?: ""}")
        listener?.invoke(Warn, message.addSpace().add(e.asTraceString).toString())
    }

    override fun error(e: Throwable?, vararg values: Any?) {
        val message = createMessage(*values)
        println("$Error: $name: $message $e ${e?.asTraceString ?: ""}")
        listener?.invoke(Error, message.addSpace().add(e.asTraceString).toString())
    }

    private fun createMessage(vararg values: Any?) = StringBuilder().apply {
        values.forEach { it?.let { add(it).add(String.Space) } }
    }.deleteLast(String.Space.length)
}

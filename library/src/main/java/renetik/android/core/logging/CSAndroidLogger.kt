package renetik.android.core.logging

import android.util.Log
import android.util.Log.getStackTraceString
import renetik.android.core.BuildConfig.LIBRARY_PACKAGE_NAME
import renetik.android.core.kotlin.primitives.Space
import renetik.android.core.kotlin.text.add
import renetik.android.core.kotlin.text.addSpace
import renetik.android.core.kotlin.text.deleteLast
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.core.logging.CSLogLevel.*

class CSAndroidLogger(
    val tag: String = LIBRARY_PACKAGE_NAME,
    override val level: CSLogLevel = if (isDebug) Debug else Error,
    val listener: CSLogListener? = null) : CSLogger {


    override fun error(vararg values: Any?) {
        if (isDisabled(Error)) return
        val message = createMessage(*values).toString()
        Log.e(tag, message)
        listener?.invoke(Error, message)
    }

    override fun error(e: Throwable, vararg values: Any?) {
        if (isDisabled(Error)) return
        val message = createMessage(*values)
        Log.e(tag, message.toString(), e)
        listener?.invoke(Error, message.addSpace().add(getStackTraceString(e)).toString())
    }

    override fun info(vararg values: Any?) {
        if (isDisabled(Info)) return
        val message = createMessage(*values).toString()
        Log.i(tag, message)
        listener?.invoke(Info, message)
    }

    override fun debug(vararg values: Any?) {
        if (isDisabled(Debug)) return
        val message = createMessage(*values).toString()
        Log.d(tag, message)
        listener?.invoke(Debug, message)
    }

    override fun debug(e: Throwable, vararg values: Any?) {
        if (isDisabled(Debug)) return
        val message = createMessage(*values)
        Log.d(tag, message.toString(), e)
        listener?.invoke(Debug, message.addSpace().add(getStackTraceString(e)).toString())
    }

    override fun warn(vararg values: Any?) {
        if (isDisabled(Warn)) return
        val message = createMessage(*values).toString()
        Log.w(tag, message)
        listener?.invoke(Warn, message)
    }

    override fun warn(e: Throwable, vararg values: Any?) {
        if (isDisabled(Warn)) return
        val message = createMessage(*values)
        Log.w(tag, message.toString(), e)
        listener?.invoke(Warn, message.addSpace().add(getStackTraceString(e)).toString())
    }

    private fun createMessage(vararg values: Any?) = StringBuilder().apply {
        values.forEach { it?.let { add(it).add(String.Space) } }
    }.deleteLast(String.Space.length)
}

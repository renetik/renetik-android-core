package renetik.android.core.logging

import android.util.Log
import android.util.Log.getStackTraceString
import renetik.android.core.BuildConfig.LIBRARY_PACKAGE_NAME
import renetik.android.core.kotlin.primitives.Space
import renetik.android.core.kotlin.text.add
import renetik.android.core.kotlin.text.addSpace
import renetik.android.core.kotlin.text.trim
import renetik.android.core.lang.CSEnvironment.isDebug
import renetik.android.core.logging.CSLogLevel.*

class CSAndroidLogger(
    val tag: String = LIBRARY_PACKAGE_NAME,
    override val level: CSLogLevel = if (isDebug) Debug else Error,
    val listener: CSLogListener? = null) : CSLogger {

    override fun debug(e: Throwable?, vararg values: Any?) {
        if (isDisabled(Debug)) return
        val message = createMessage(*values)
        Log.d(tag, message.toString(), e)
        listener?.invoke(Debug,
            message.addSpace().add(getStackTraceString(e)).trim().toString())
    }

    override fun info(e: Throwable?, vararg values: Any?) {
        if (isDisabled(Info)) return
        val message = createMessage(*values)
        Log.i(tag, message.toString(), e)
        listener?.invoke(Info,
            message.addSpace().add(getStackTraceString(e)).trim().toString())
    }

    override fun warn(e: Throwable?, vararg values: Any?) {
        if (isDisabled(Warn)) return
        val message = createMessage(*values)
        Log.w(tag, message.toString(), e)
        listener?.invoke(Warn,
            message.addSpace().add(getStackTraceString(e)).trim().toString())
    }

    override fun error(e: Throwable?, vararg values: Any?) {
        if (isDisabled(Error)) return
        val message = createMessage(*values)
        Log.e(tag, message.toString(), e)
        listener?.invoke(Error,
            message.addSpace().add(getStackTraceString(e)).trim().toString())
    }

    private fun createMessage(vararg values: Any?) = StringBuilder().apply {
        values.forEach { it?.let { add(it).add(String.Space) } }
    }.trim()
}

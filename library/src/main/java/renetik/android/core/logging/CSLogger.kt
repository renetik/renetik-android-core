package renetik.android.core.logging

interface CSLogger {
    val level: CSLogLevel
    fun debug(e: Throwable?, vararg values: Any?)
    fun info(e: Throwable?, vararg values: Any?)
    fun warn(e: Throwable?, vararg values: Any?)
    fun error(e: Throwable?, vararg values: Any?)
}
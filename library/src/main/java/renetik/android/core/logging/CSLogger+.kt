package renetik.android.core.logging

fun CSLogger.isEnabled(level: CSLogLevel) = !isDisabled(level)

fun CSLogger.isDisabled(level: CSLogLevel) = level.ordinal < this.level.ordinal
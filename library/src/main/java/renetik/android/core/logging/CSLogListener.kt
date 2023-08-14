package renetik.android.core.logging

import renetik.android.core.lang.void

typealias CSLogListener = (level: CSLogLevel, message: String, ex: Throwable?) -> void
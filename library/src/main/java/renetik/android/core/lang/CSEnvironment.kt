package renetik.android.core.lang

import android.app.Application
import renetik.android.core.extensions.content.isDebug
import renetik.android.core.kotlin.createClass
import renetik.android.core.kotlin.invoke
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.core.logging.CSLogMessage.Companion.message

object CSEnvironment {
    var app: Application by lazyVar {
        try {
            createClass<Any>("android.app.ActivityThread")
                ?.invoke("currentApplication") as Application
        } catch (ex: Throwable) {
            logWarn { message(ex) }
            throw Exception("Getting Application from ActivityThread failed, " +
                    "consider setting it manually.")
        }
    }

    val isDebug by lazy { app.isDebug }
}



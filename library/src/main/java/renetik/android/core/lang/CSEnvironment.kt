package renetik.android.core.lang

import android.app.Application
import android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE
import renetik.android.core.kotlin.createClass
import renetik.android.core.kotlin.invoke
import renetik.android.core.kotlin.primitives.isFlagSet
import renetik.android.core.logging.CSLog.logWarn

object CSEnvironment {
    var app: Application by lazyVar {
        try {
            createClass<Any>("android.app.ActivityThread")
                ?.invoke("currentApplication") as Application
        } catch (ex: Throwable) {
            logWarn(ex)
            throw Exception("Getting Application from ActivityThread failed, " +
                    "consider setting it manually.")
        }
    }

    val isDebug by lazy { app.applicationInfo.flags isFlagSet FLAG_DEBUGGABLE }
}



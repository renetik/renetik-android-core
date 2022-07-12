package renetik.android.core.lang

import android.app.Application
import android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE
import renetik.android.core.kotlin.createClass
import renetik.android.core.kotlin.invokeFunction
import renetik.android.core.kotlin.primitives.isFlagSet

object CSEnvironment {
    var app: Application by lazyVar {
        try {
            createClass<Any>("android.app.ActivityThread")
                ?.invokeFunction("currentApplication") as Application
        } catch (ex: Throwable) {
            throw Exception("Getting Application from ActivityThread failed, " +
                    "consider setting it manually.")
        }
    }

    val isDebug by lazy { app.applicationInfo.flags isFlagSet FLAG_DEBUGGABLE }
}
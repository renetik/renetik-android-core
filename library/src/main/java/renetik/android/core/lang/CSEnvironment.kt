package renetik.android.core.lang

import android.app.Activity
import android.app.Application
import android.os.Build.*
import renetik.android.core.base.CSApplication
import renetik.android.core.extensions.content.isDebug
import renetik.android.core.kotlin.classExist
import renetik.android.core.kotlin.createClass
import renetik.android.core.kotlin.invoke
import renetik.android.core.kotlin.isNull
import renetik.android.core.kotlin.primitives.isTrue
import renetik.android.core.logging.CSLog.logInfo
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

    val activity: Activity?
        get() = (app as? CSApplication)?.activity.also {
            if (it.isNull) logWarn {
                message("This method of getting activity" +
                        " depends on using CSApplication as base class for Application")
            }
        }

    val isDebug by lazy { app.isDebug }

    val isTestRunner by lazy { classExist("org.junit.runner.Runner") }

    val isEmulator: Boolean by lazy {
        (BRAND.startsWith("generic") && DEVICE.startsWith("generic")
                || FINGERPRINT.startsWith("generic")
                || FINGERPRINT.startsWith("unknown")
                || HARDWARE.contains("goldfish")
                || HARDWARE.contains("ranchu")
                || MODEL.contains("google_sdk")
                || MODEL.contains("Emulator")
                || MODEL.contains("Android SDK built for x86")
                || MANUFACTURER.contains("Genymotion")
                || PRODUCT.contains("sdk_google")
                || PRODUCT.contains("google_sdk")
                || PRODUCT.contains("sdk")
                || PRODUCT.contains("sdk_x86")
                || PRODUCT.contains("sdk_gphone64_arm64")
                || PRODUCT.contains("vbox86p")
                || PRODUCT.contains("emulator")
                || PRODUCT.contains("simulator")).apply {
            if (isTrue) logInfo { message("Running in emulator") }
        }
    }
}



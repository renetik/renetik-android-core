package renetik.android.core.base

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import renetik.android.core.lang.CSEnvironment
import renetik.android.core.logging.CSLog.logError
import renetik.android.core.logging.CSLog.logInfo
import renetik.android.core.logging.CSLog.logWarn
import renetik.android.core.logging.CSLogMessage.Companion.message
import kotlin.reflect.KClass

abstract class CSApplication : Application(), ActivityLifecycleCallbacks {

    companion object {
        val app get() = CSEnvironment.app as CSApplication
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        logWarn { message("onLowMemory") }
    }

    override fun onTerminate() {
        super.onTerminate()
        logInfo { message("onTerminate") }
    }

    abstract val activityType: KClass<out Activity>

    var activity: Activity? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (!activityType.isInstance(activity)) return
        if (this.activity?.isDestroyed == false ||
            this.activity?.isFinishing == false)
            logError {
                message("activity should be destroyed or null, " +
                        "when new is created, in single activity application")
            }
        this.activity = activity
    }

    override fun onActivityStarted(activity: Activity) = Unit

    override fun onActivityResumed(activity: Activity) = Unit

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) {
        if (!activityType.isInstance(activity)) return
        this.activity = null
    }
}
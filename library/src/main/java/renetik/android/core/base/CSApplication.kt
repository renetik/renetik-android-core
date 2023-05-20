package renetik.android.core.base

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import renetik.android.core.kotlin.notImplemented
import renetik.android.core.lang.CSEnvironment
import renetik.android.core.logging.CSLog.logError
import renetik.android.core.logging.CSLog.logInfo
import renetik.android.core.logging.CSLog.logWarn
import kotlin.reflect.KClass

abstract class CSApplication<ActivityType : AppCompatActivity>
    : Application(), ActivityLifecycleCallbacks {

    companion object {
        val app get() = CSEnvironment.app as CSApplication<*>
    }

    override fun onCreate() {
        super.onCreate()
        CSEnvironment.app = this
        registerActivityLifecycleCallbacks(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        logWarn { "onLowMemory" }
    }

    override fun onTerminate() {
        super.onTerminate()
        logInfo { "onTerminate" }
    }

    abstract val activityClass: KClass<out ActivityType>

    var activity: ActivityType? = null

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (!activityClass.isInstance(activity)) return
        if (this.activity?.isDestroyed == false ||
            this.activity?.isFinishing == false
        )
            logError {
                "activity should be destroyed or null, " +
                    "when new is created, in single activity application"
            }
        @Suppress("UNCHECKED_CAST")
        this.activity = activity as ActivityType
    }

    override fun onActivityStarted(activity: Activity) = Unit

    override fun onActivityResumed(activity: Activity) = Unit

    override fun onActivityPaused(activity: Activity) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityDestroyed(activity: Activity) {
        if (!activityClass.isInstance(activity)) return
        if (this.activity == activity) this.activity = null
    }

    open fun restart(): Unit = notImplemented()
}
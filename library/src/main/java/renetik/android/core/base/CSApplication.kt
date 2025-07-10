package renetik.android.core.base

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Process.killProcess
import android.os.Process.myPid
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import renetik.android.core.kotlin.notImplemented
import renetik.android.core.kotlin.primitives.isTrue
import renetik.android.core.lang.CSEnvironment
import renetik.android.core.lang.Func
import renetik.android.core.logging.CSLog.logError
import renetik.android.core.logging.CSLog.logInfo
import renetik.android.core.logging.CSLog.logWarn
import kotlin.reflect.KClass
import kotlin.system.exitProcess

abstract class CSApplication<ActivityType : AppCompatActivity>
    : Application(), ActivityLifecycleCallbacks {

    companion object {
        val app get() = CSEnvironment.app as CSApplication<*>

        fun getString(@StringRes resId: Int): String =
            app.localizationContext.getString(resId)

        fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String =
            app.localizationContext.getString(resId, *formatArgs)
    }

    override fun onCreate() {
        super.onCreate()
        CSEnvironment.app = this
        registerDefaultUncaughtExceptionHandler()
        registerActivityLifecycleCallbacks(this)
    }

    private fun registerDefaultUncaughtExceptionHandler() {
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            val isNotAttachedError = throwable is IllegalArgumentException
                    && throwable.message?.contains("not attached to window manager").isTrue
            if (isNotAttachedError) logError(throwable, "Ignored window removal exception")
            else defaultHandler?.uncaughtException(thread, throwable)
        }
    }

    @Deprecated("Deprecated in Java")
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
        protected set

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (!activityClass.isInstance(activity)) return
        if (this.activity?.isDestroyed == false ||
            this.activity?.isFinishing == false
        ) logError(
            "activity should be destroyed or null, " +
                    "when new is created, in single activity application"
        )
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

    open fun restart(onShutdown: Func? = null): Unit = notImplemented()

    open fun exit() {
        logInfo()
        killProcess(myPid())
        exitProcess(0)
    }

    open fun hardRestart() {
        logInfo()
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent == null) {
            logWarn()
            killProcess(myPid())
            exitProcess(1)
        } else {
            val intent = Intent.makeRestartActivityTask(launchIntent.component)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            killProcess(myPid())
            exitProcess(0)
        }
    }

    open val localizationContext get():Context = this
}
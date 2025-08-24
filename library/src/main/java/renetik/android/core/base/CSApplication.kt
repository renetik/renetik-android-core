package renetik.android.core.base

import android.app.Activity
import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import renetik.android.core.extensions.content.CSToast.toast
import renetik.android.core.kotlin.findCause
import renetik.android.core.kotlin.notImplemented
import renetik.android.core.kotlin.primitives.isTrue
import renetik.android.core.kotlin.unexpected
import renetik.android.core.lang.CSEnvironment
import renetik.android.core.lang.CSHandler.mainHandler
import renetik.android.core.lang.CSLang.exit
import renetik.android.core.lang.Func
import renetik.android.core.lang.send
import renetik.android.core.lang.variable.CSWeakVariable.Companion.weak
import renetik.android.core.logging.CSLog.logError
import renetik.android.core.logging.CSLog.logInfo
import renetik.android.core.logging.CSLog.logWarn
import kotlin.reflect.KClass

abstract class CSApplication<ActivityType : AppCompatActivity> : Application(),
    ActivityLifecycleCallbacks {

    companion object {
        val app get() = CSEnvironment.app as CSApplication<*>

        fun getString(@StringRes resId: Int): String = app.localizationContext.getString(resId)

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
            when {
                throwable.isNotAttachedError() ->
                    logError(throwable, "Ignored window removal exception")
                throwable.isIncrementalInstallMissingResource() -> {
                    toast("App installation is corrupted."); exit(status = 0)
                }
                else -> defaultHandler?.uncaughtException(thread, throwable)
            }
        }
    }

    private fun Throwable.isNotAttachedError(): Boolean = findCause {
        (it is IllegalArgumentException && message?.contains("not attached to window manager").isTrue)
                || (it is WindowManager.BadTokenException)
    }

    private fun Throwable.isIncrementalInstallMissingResource(): Boolean = findCause {
        (it as? Resources.NotFoundException)?.message.let { message ->
            message?.contains("not fully present", ignoreCase = true).isTrue ||
                    message?.contains("file not fully present", ignoreCase = true).isTrue ||
                    message?.contains("incremental", ignoreCase = true).isTrue
        }
    }

    @Deprecated("Deprecated in Java") override fun onLowMemory() {
        super.onLowMemory()
        logWarn { "onLowMemory" }
    }

    override fun onTerminate() {
        super.onTerminate()
        logInfo { "onTerminate" }
    }

    abstract val activityClass: KClass<out ActivityType>

    var activity: ActivityType? by weak()
        protected set

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (!activityClass.isInstance(activity)) return
        if (this.activity?.isDestroyed == false || this.activity?.isFinishing == false)
            logError("activity should be destroyed or null, " + "when new is created, in single activity application")
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
        exit(status = 0)
    }

    open fun hardRestart() {
        logInfo()
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent == null) {
            logWarn()
            exit(status = 1)
        } else {
            val intent = Intent.makeRestartActivityTask(launchIntent.component)
            startActivity(intent)
            exit(status = 0)
        }
    }

    open val localizationContext get():Context = this
}
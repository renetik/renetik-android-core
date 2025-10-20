package renetik.android.core.extensions.app

import android.app.Application
import android.content.Intent
import renetik.android.core.lang.CSLang.ExitStatus.Error
import renetik.android.core.lang.CSLang.ExitStatus.OK
import renetik.android.core.lang.CSLang.exit
import renetik.android.core.logging.CSLog.logInfo
import renetik.android.core.logging.CSLog.logWarn

fun Application.exitStart() {
    logInfo("Application Restart")
    val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
    if (launchIntent == null) {
        logWarn()
        exit(Error)
    } else {
        val intent = Intent.makeRestartActivityTask(launchIntent.component)
        startActivity(intent)
        exit(OK)
    }
}
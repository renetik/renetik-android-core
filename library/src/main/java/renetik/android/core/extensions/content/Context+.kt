package renetik.android.core.extensions.content

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.*
import android.content.ContextWrapper.CONNECTIVITY_SERVICE
import android.content.Intent.ACTION_BATTERY_CHANGED
import android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.*
import android.net.Uri
import android.os.BatteryManager.EXTRA_LEVEL
import android.os.BatteryManager.EXTRA_SCALE
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.Q
import android.util.Base64
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.res.getDrawableOrThrow
import renetik.android.core.kotlin.primitives.isFlagSet
import renetik.android.core.kotlin.primitives.isSet
import renetik.android.core.lang.catchAllErrorReturnNull
import renetik.android.core.lang.catchWarnReturnNull
import renetik.android.core.lang.void
import renetik.android.core.logging.CSLog
import renetik.android.core.logging.CSLogMessage
import java.security.MessageDigest
import java.util.*


val Context.isDebug get() = applicationInfo.flags isFlagSet FLAG_DEBUGGABLE

@Suppress("UNCHECKED_CAST")
fun <ViewType : View> Context.inflate(layoutId: Int) =
    LayoutInflater.from(this).inflate(layoutId, null) as ViewType

/**
 * If the item does not have an icon, the item's default icon is returned
 * such as the default activity icon.
 */
val Context.applicationIcon: Drawable get() = applicationInfo.loadIcon(packageManager)
val Context.applicationLabel: String get() = "${applicationInfo.loadLabel(packageManager)}"
val Context.applicationLogo: Drawable? get() = applicationInfo.loadLogo(packageManager)

val Context.isNetworkConnected: Boolean
    get() {
        val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (SDK_INT >= Q)
            manager.getNetworkCapabilities(manager.activeNetwork)?.let {
                it.hasTransport(TRANSPORT_WIFI) ||
                        it.hasTransport(TRANSPORT_CELLULAR) ||
                        it.hasTransport(TRANSPORT_BLUETOOTH) ||
                        it.hasTransport(TRANSPORT_ETHERNET) ||
                        it.hasTransport(TRANSPORT_VPN)
            } ?: false
        else
            @Suppress("DEPRECATION")
            manager.activeNetworkInfo?.isConnected == true
    }

@Suppress("DEPRECATION")
val Context.packageVersionString
    get() = packageInfo!!.versionCode.toString() + "-" + packageInfo!!.versionName

@Suppress("DEPRECATION")
val Context.packageVersionCode
    get() = packageInfo!!.versionCode

@Suppress("DEPRECATION")
val Context.appKeyHash
    @SuppressLint("PackageManagerGetSignatures")
    get() = catchAllErrorReturnNull {
        val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        if (info.signatures.isSet) {
            val messageDigest = MessageDigest.getInstance("SHA")
            messageDigest.update(info.signatures[0].toByteArray())
            Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT)
        } else null
    }

val Context.packageInfo
    get() = catchWarnReturnNull<PackageInfo, NameNotFoundException> {
        packageManager.getPackageInfo(packageName, 0)
    }

@Suppress("DEPRECATION")
@SuppressLint("UseCompatLoadingForDrawables")
fun Context.getDrawable(name: String): Drawable? {
    val resourceId = resources.getIdentifier(name, "drawable", packageName)
    return resources.getDrawable(resourceId)
}

//fun Context.getColorResource(name: String): Int? {
//    val colorResource = resources.getIdentifier(name, "color", packageName)
//    return if (colorResource == 0) null else colorResource
//}

//fun Context.getColor(name: String): CSColorInt? = getColorResource(name)?.let { color(it) }

val Context.progressDrawable: Drawable
    get() {
        val value = TypedValue()
        theme.resolveAttribute(android.R.attr.progressBarStyleSmall, value, false)
        val progressBarStyle = value.data
        val attributes = intArrayOf(android.R.attr.indeterminateDrawable)
        val array = obtainStyledAttributes(progressBarStyle, attributes)
        val drawable = array.getDrawableOrThrow(0)
        array.recycle()
        (drawable as? Animatable)?.start()
        return drawable
    }


fun BroadcastReceiver(function: (context: Context, intent: Intent) -> Unit) =
    object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) = function(context, intent)
    }

fun Context.register(action: String, function: () -> void): BroadcastReceiver =
    register(IntentFilter(action)) { _, _ -> function() }

fun Context.broadcastPendingIntent(actionId: String, flags: Int): PendingIntent =
    PendingIntent.getBroadcast(this, 0, Intent(actionId), flags)

fun Context.register(action: String,
                     function: (Intent, BroadcastReceiver) -> void): BroadcastReceiver =
    register(IntentFilter(action), function)

fun Context.register(intent: IntentFilter,
                     function: (Intent, BroadcastReceiver) -> void): BroadcastReceiver {
    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) = function(intent, this)
    }
    registerReceiver(receiver, intent)
    return receiver
}

fun Context.unregister(receiver: BroadcastReceiver) {
    unregisterReceiver(receiver)
}

val Context.batteryPercent: Float
    get() {
        val batteryStatus = registerReceiver(null, IntentFilter(ACTION_BATTERY_CHANGED))
        val level = batteryStatus!!.getIntExtra(EXTRA_LEVEL, -1)
        val scale = batteryStatus.getIntExtra(EXTRA_SCALE, -1)
        return level / scale.toFloat()
    }

fun Context.string(@StringRes resId: Int): String {
    return resources.getString(resId)
}

fun Context.setLocale(locale: Locale) {
    Locale.setDefault(locale)
    val config = resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)
    @Suppress("DEPRECATION")
    resources.updateConfiguration(config, resources.displayMetrics)
}

fun Context.createContextForLocale(locale: Locale): Context {
    Locale.setDefault(locale)
    val config = resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)
    return createConfigurationContext(config)
}

val Context.isPlayStoreInstalled get() = isPackageInstalled("com.android.vending")

@Suppress("DEPRECATION")
fun Context.isPackageInstalled(packageName: String): Boolean = try {
    getPackageManager().getPackageInfo(packageName, 0)
    true
} catch (e: NameNotFoundException) {
    false
}

fun Context.goHome() = startActivity(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME))

fun Context.startApplication(packageName: String) {
    try {
        val intent = Intent("android.intent.action.MAIN")
        intent.addCategory("android.intent.category.LAUNCHER")
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        val resolveInfoList = packageManager.queryIntentActivities(intent, 0)
        for (info in resolveInfoList)
            if (info.activityInfo.packageName.equals(packageName, ignoreCase = true)) {
                launchComponent(info.activityInfo.packageName, info.activityInfo.name)
                return
            }
        showInMarket(packageName)
    } catch (e: Exception) {
        showInMarket(packageName)
    }
}

private fun Context.launchComponent(packageName: String, name: String) {
    val intent = Intent("android.intent.action.MAIN")
    intent.addCategory("android.intent.category.LAUNCHER")
    intent.component = ComponentName(packageName, name)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}

private fun Context.showInMarket(packageName: String?) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName!!))
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(intent)
}

fun Context.startActivityForUri(
    uri: Uri, onActivityNotFound: ((ActivityNotFoundException) -> Unit)? = null) =
    startActivityForUriAndType(uri, null, onActivityNotFound)

fun Context.startActivityForUriAndType(
    uri: Uri, type: String?, onActivityNotFound: ((ActivityNotFoundException) -> Unit)? = null) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(uri, type)
    // Grant Permission to a Specific Package
    // https://developer.android.com/reference/androidx/core/content/FileProvider
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_GRANT_READ_URI_PERMISSION
    intent.clipData = ClipData.newRawUri("", uri)
    try {
        startActivity(intent)
    } catch (exception: ActivityNotFoundException) {
        CSLog.logWarn { CSLogMessage.message(exception) }
        onActivityNotFound?.invoke(exception)
    }
}

fun Context.openUrl(url: String) =
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
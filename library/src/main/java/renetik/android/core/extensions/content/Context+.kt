package renetik.android.core.extensions.content

import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.ClipData
import android.content.ComponentName
import android.content.Context
import android.content.ContextWrapper.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.Intent.ACTION_BATTERY_CHANGED
import android.content.IntentFilter
import android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE
import android.content.pm.PackageInfo
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_BLUETOOTH
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_VPN
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.net.Uri
import android.os.BatteryManager.EXTRA_LEVEL
import android.os.BatteryManager.EXTRA_SCALE
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.Q
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.RECEIVER_EXPORTED
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
import androidx.core.net.toUri
import renetik.android.core.kotlin.collections.list
import renetik.android.core.kotlin.primitives.isFlagSet
import renetik.android.core.lang.catchWarnReturnNull
import renetik.android.core.logging.CSLog.logWarn
import java.util.Locale

val Context.isDebug get() = applicationInfo.flags isFlagSet FLAG_DEBUGGABLE

/**
 * If the item does not have an icon, the item's default icon is returned
 * such as the default activity icon.
 */
val Context.applicationIcon: Drawable get() = applicationInfo.loadIcon(packageManager)
val Context.applicationLabel: String
    get() = "${
        applicationInfo.loadLabel(packageManager)
    }"
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

//@Suppress("DEPRECATION")
//val Context.appKeyHash
//    @SuppressLint("PackageManagerGetSignatures")
//    get() = catchAllErrorReturnNull {
//        val info =
//            packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//        if (info.signatures.isSet) {
//            val messageDigest = MessageDigest.getInstance("SHA")
//            messageDigest.update(info.signatures[0].toByteArray())
//            Base64.encodeToString(messageDigest.digest(), Base64.DEFAULT)
//        } else null
//    }

val Context.packageInfo
    get() = catchWarnReturnNull<PackageInfo, NameNotFoundException> {
        packageManager.getPackageInfo(packageName, 0)
    }

inline fun BroadcastReceiver(
    crossinline function: (context: Context, intent: Intent) -> Unit) =
    object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) =
            function(context, intent)
    }

inline fun Context.register(
    action: String, exported: Boolean = false, crossinline function: () -> Unit
): BroadcastReceiver = register(IntentFilter(action), exported) { _, _ -> function() }


fun Context.broadcastPendingIntent(actionId: String, flags: Int): PendingIntent =
    PendingIntent.getBroadcast(this, 0, Intent(actionId), flags)

inline fun Context.register(
    action: String, crossinline function: (Intent, BroadcastReceiver) -> Unit
): BroadcastReceiver = register(IntentFilter(action), function = function)

inline fun Context.register(
    intent: IntentFilter, exported: Boolean = false,
    crossinline function: (Intent, BroadcastReceiver) -> Unit
): BroadcastReceiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) = function(intent, this)
}.also { register(it, intent, exported) }

fun Context.register(intent: IntentFilter): Intent? = register(null, intent)

fun Context.register(
    receiver: BroadcastReceiver?, intent: IntentFilter,
    exported: Boolean = false): Intent? =
    ContextCompat.registerReceiver(this, receiver, intent,
        if (exported) RECEIVER_EXPORTED else RECEIVER_NOT_EXPORTED)

fun Context.unregister(receiver: BroadcastReceiver) {
    runCatching { unregisterReceiver(receiver) }.onFailure(::logWarn)
}

val Context.batteryPercent: Float
    get() {
        val batteryStatus = register(IntentFilter(ACTION_BATTERY_CHANGED))
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

fun Context.isPackageInstalled(packageName: String): Boolean = try {
    packageManager.getPackageInfo(packageName, 0)
    true
} catch (e: NameNotFoundException) {
    false
}

fun Context.goHome() =
    startActivity(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME))

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

private fun Context.showInMarket(packageName: String?) =
    openUrl("market://details?id=" + packageName!!)

fun Context.startActivityForUri(
    uri: Uri, onActivityNotFound: ((ActivityNotFoundException) -> Unit)? = null
) = startActivityForUriAndType(uri, null, onActivityNotFound)

fun Context.startActivityForUriAndType(
    uri: Uri, type: String? = null,
    onActivityNotFound: ((ActivityNotFoundException) -> Unit)? = null
) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setDataAndType(uri, type)
    // Grant Permission to a Specific Package
    // https://developer.android.com/reference/androidx/core/content/FileProvider
    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_GRANT_READ_URI_PERMISSION
    intent.clipData = ClipData.newRawUri("", uri)
    try {
        startActivity(intent)
    } catch (exception: ActivityNotFoundException) {
        logWarn(exception)
        onActivityNotFound?.invoke(exception)
    }
}

fun Context.openUrl(url: String, errorMessage: String? = null) {
    var intent = Intent(Intent.ACTION_VIEW, url.toUri())
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    fun startChooser() = try {
        intent = Intent.createChooser(intent, null)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    } catch (e: Exception) {
        errorMessage?.also(::toast)
        logWarn(e) { "Failed to open url: $url" }
    }
    runCatching {
        if (intent.resolveActivity(packageManager) != null) startActivity(intent)
        else startChooser()
    }.onFailure { startChooser() }
}

fun Context.getDeniedPermissions(permissions: List<String>): Array<String> {
    val deniedPermissions = list<String>()
    for (permission in permissions)
        if (!isPermissionGranted(permission)) deniedPermissions.add(permission)
    return deniedPermissions.toTypedArray()
}

fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PERMISSION_GRANTED
}

fun Context.isPermissionsGranted(vararg permissions: String): Boolean =
    permissions.all { isPermissionGranted(it) }

fun Context.isPermissionsGranted(permissions: Iterable<String>): Boolean =
    permissions.all { isPermissionGranted(it) }
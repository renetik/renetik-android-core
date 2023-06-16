package renetik.android.core.extensions.content

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.ContextWrapper.ACTIVITY_SERVICE
import android.content.res.Configuration.*
import android.content.res.Resources.NotFoundException
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment.getExternalStorageDirectory
import android.text.Html
import android.text.Spanned
import android.util.DisplayMetrics
import android.util.DisplayMetrics.DENSITY_DEFAULT
import android.util.TypedValue
import android.util.TypedValue.*
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat.getColor
import renetik.android.core.R
import renetik.android.core.kotlin.asString
import renetik.android.core.kotlin.collections.list
import renetik.android.core.kotlin.equalsAny
import renetik.android.core.lang.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.lang.Integer.MAX_VALUE

class CSColorInt(@ColorInt val color: Int)

fun Context.formatted(resId: Int): Spanned =
    Html.fromHtml(
        getString(resId).replace("\n", "<br>")
            .replace("[B]", "<b>").replace("[/B]", "</b>"), 0
    )

fun Context.color(@ColorRes color: Int) = CSColorInt(getColor(this, color))
fun Context.colorInt(@ColorInt color: Int) = CSColorInt(color)

fun Context.resourceBytes(id: Int) = catchAllWarn {
    val stream = resources.openRawResource(id)
    val outputStream = ByteArrayOutputStream()
    val buffer = ByteArray(4 * 1024)
    tryAndFinally({
        var read: Int
        do {
            read = stream.read(buffer, 0, buffer.size)
            if (read == -1) break
            outputStream.write(buffer, 0, read)
        } while (true)
        outputStream.toByteArray()
    }) { stream.close() }
}

fun Context.openInputStream(uri: Uri) = catchErrorReturnNull<FileNotFoundException, InputStream> {
    return contentResolver.openInputStream(uri)
}

fun Context.resourceDimensionPx(@DimenRes id: Int) = resources.getDimension(id).toInt()

fun Context.resourceStrings(id: Int) = catchWarnReturnNull<List<String>, NotFoundException> {
    list(*resources.getStringArray(id))
}

fun Context.resourceInts(id: Int) = catchError<NotFoundException> {
    list(resources.getIntArray(id).asList())
}

val Context.displayMetrics get():DisplayMetrics = resources.displayMetrics

private const val LOW_DPI_STATUS_BAR_HEIGHT = 19
private const val MEDIUM_DPI_STATUS_BAR_HEIGHT = 25
private const val HIGH_DPI_STATUS_BAR_HEIGHT = 38

val Context.statusBarHeight
    get() = when (displayMetrics.densityDpi) {
        DisplayMetrics.DENSITY_HIGH -> HIGH_DPI_STATUS_BAR_HEIGHT
        DisplayMetrics.DENSITY_MEDIUM -> MEDIUM_DPI_STATUS_BAR_HEIGHT
        DisplayMetrics.DENSITY_LOW -> LOW_DPI_STATUS_BAR_HEIGHT
        else -> MEDIUM_DPI_STATUS_BAR_HEIGHT
    }

fun Context.toDpF(pixel: Float) = pixel / (displayMetrics.densityDpi.toFloat() / DENSITY_DEFAULT)
fun Context.toDpF(pixel: Int) = toDpF(pixel.toFloat())
fun Context.toDp(pixel: Int) = toDpF(pixel).toInt()

fun Context.dpToPixelF(dp: Float): Float = applyDimension(COMPLEX_UNIT_DIP, dp, displayMetrics)
fun Context.dpToPixelF(dp: Int): Float = dpToPixelF(dp.toFloat())
fun Context.dpToPixel(dp: Float) = dpToPixelF(dp).toInt()
fun Context.dpToPixel(dp: Int) = dpToPixelF(dp.toFloat()).toInt()

fun Context.spToPixelF(sp: Float) = applyDimension(COMPLEX_UNIT_SP, sp, displayMetrics)
fun Context.spToPixelF(sp: Int) = dpToPixelF(sp.toFloat())
fun Context.spToPixel(sp: Float) = dpToPixelF(sp).toInt()
fun Context.spToPixel(sp: Int) = dpToPixelF(sp.toFloat()).toInt()

private fun Context.attributeValue(@AttrRes attribute: Int) =
    TypedValue().apply { theme.resolveAttribute(attribute, this, true) }

@ColorInt
fun Context.attributeColor(@AttrRes attribute: Int) =
    attributeValue(attribute).data.apply {
        if (this == 0) throw NotFoundException()
    }

fun Context.attributeDimensionPixel(@AttrRes attribute: Int, default: Int = 0): Int {
    val attributes = obtainStyledAttributes(intArrayOf(attribute))
    val dimension = attributes.getDimensionPixelSize(0, default)
    attributes.recycle()
    return dimension
}

fun Context.attributeDimension(@AttrRes attribute: Int, default: Float = 0f): Float {
    val attributes = obtainStyledAttributes(intArrayOf(attribute))
    val dimension = attributes.getDimension(0, default)
    attributes.recycle()
    return dimension
}

fun Context.attributeInt(@AttrRes attribute: Int, default: Int = 0): Int {
    val attributes = obtainStyledAttributes(intArrayOf(attribute))
    val value = attributes.getInt(0, default)
    attributes.recycle()
    return value
}

fun Context.attributeFloat(@AttrRes attribute: Int, default: Float = 0f): Float {
    val attributes = obtainStyledAttributes(intArrayOf(attribute))
    val float = attributes.getFloat(0, default)
    attributes.recycle()
    return float
}

fun Context.attributeString(@AttrRes attribute: Int) = attributeValue(attribute).string.asString

fun Context.attributeString2(@AttrRes attribute: Int) = attributeString(intArrayOf(attribute), 0)

fun Context.attributeString(styleable: IntArray, styleableAttribute: Int): String {
    val attributes = obtainStyledAttributes(styleable)
    val string = attributes.getString(styleableAttribute)
    attributes.recycle()
    return string.asString
}

fun Context.attributeResourceId(@AttrRes attribute: Int) =
    attributeValue(attribute).resourceId.apply {
        if (this == 0) throw NotFoundException()
    }

fun Context.assetsReadText(path: String) = assets.open(path).bufferedReader().use { it.readText() }

val Context.isPortrait get() = resources.configuration.orientation == ORIENTATION_PORTRAIT
val Context.isLandscape get() = !isPortrait
val Context.isTablet get() = resources.getBoolean(R.bool.cs_is_tablet)
val Context.isPhone get() = !isTablet

fun Context.drawable(@DrawableRes resource: Int) =
    AppCompatResources.getDrawable(this, resource)!!.apply {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
    }

fun clearDrawable(drawable: Drawable) = clearDrawable(drawable.bounds)
fun clearDrawable(bounds: Rect) = ColorDrawable().apply { this.bounds = bounds }

val Context.isDarkMode
    get() = if (getDefaultNightMode()
            .equalsAny(MODE_NIGHT_FOLLOW_SYSTEM, MODE_NIGHT_UNSPECIFIED)
    ) isSystemDarkMode
    else getDefaultNightMode() == MODE_NIGHT_YES

val Context.isSystemDarkMode
    get() = resources.configuration.uiMode and UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES

fun Context.isServiceRunning(serviceClass: Class<out Service>): Boolean {
    val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
    @Suppress("DEPRECATION")
    for (running in activityManager.getRunningServices(MAX_VALUE))
        if (serviceClass.name == running.service.className) return true
    return false
}

val Context.externalFilesDir: File
    get() = getExternalFilesDir(null) ?: getExternalStorageDirectory()
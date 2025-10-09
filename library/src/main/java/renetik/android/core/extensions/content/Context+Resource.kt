package renetik.android.core.extensions.content

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.ContextWrapper.ACTIVITY_SERVICE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import android.util.TypedValue.COMPLEX_UNIT_DIP
import android.util.TypedValue.COMPLEX_UNIT_SP
import android.util.TypedValue.applyDimension
import android.view.ContextThemeWrapper
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.app.AppCompatDelegate.getDefaultNightMode
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.getColorOrThrow
import androidx.core.content.res.use
import renetik.android.core.kotlin.asString
import renetik.android.core.kotlin.equalsAny
import renetik.android.core.lang.catchAllWarn
import renetik.android.core.lang.catchErrorReturnNull
import renetik.android.core.lang.tryAndFinally
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.lang.Integer.MAX_VALUE

fun Context.formatted(@StringRes resId: Int): Spanned =
    Html.fromHtml(
        getString(resId).replace("\n", "<br>")
            .replace("[B]", "<b>").replace("[/B]", "</b>"), 0
    )


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

fun Context.openInputStream(uri: Uri) =
    catchErrorReturnNull<FileNotFoundException, InputStream> {
        return contentResolver.openInputStream(uri)
    }

fun Context.dimensionInt(@DimenRes id: Int) = dimension(id).toInt()
fun Context.dimension(@DimenRes id: Int): Float = resources.getDimension(id)
fun Context.dimensionPx(@DimenRes id: Int): Int = resources.getDimensionPixelSize(id)
fun Context.dimensionPxFloat(@DimenRes id: Int): Float =
    resources.getDimensionPixelSize(id).toFloat()

fun Context.strings(id: Int) = resources.getStringArray(id).toList()
fun Context.ints(id: Int): List<Int> = resources.getIntArray(id).toList()

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

fun Context.toDpF(pixel: Float) =
    pixel / (displayMetrics.densityDpi.toFloat() / DENSITY_DEFAULT)

fun Context.toDpF(pixel: Int) = toDpF(pixel.toFloat())
fun Context.toDp(pixel: Int) = toDpF(pixel).toInt()

fun Context.dpToPixelF(dp: Float): Float =
    applyDimension(COMPLEX_UNIT_DIP, dp, displayMetrics)

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
fun Context.themeAttributeColor(theme: Int, attribute: Int): Int =
    ContextThemeWrapper(this, theme).attributeColor(attribute)

@ColorInt
fun Context.resourceColor(@ColorRes resource: Int): Int = getColor(resource)

@ColorInt
fun Context.attributeColor(@AttrRes attribute: Int): Int =
    attributeValue(attribute).data.apply {
        if (this == 0) throw NotFoundException()
    }

fun Context.attributeDrawable(@AttrRes attribute: Int): Drawable? =
    attributeValue(attribute).let { ContextCompat.getDrawable(this, it.resourceId) }

@ColorInt
fun Context.attributeColorOrNull(@AttrRes attribute: Int): Int? =
    attributeValue(attribute).data.takeIf { it != 0 }

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

fun Context.attributeString(@AttrRes attribute: Int) =
    attributeValue(attribute).string.asString

fun Context.attributeString2(@AttrRes attribute: Int) =
    attributeString(intArrayOf(attribute), 0)

fun Context.attributeString(styleable: IntArray, styleableAttribute: Int): String {
    val attributes = obtainStyledAttributes(styleable)
    val string = attributes.getString(styleableAttribute)
    attributes.recycle()
    return string.asString
}

fun Context.attributeResourceId(@AttrRes attribute: Int) = attributeValue(attribute)
    .resourceId.apply { if (this == 0) throw NotFoundException() }

fun Context.assetsReadText(path: String) =
    assets.open(path).bufferedReader().use { it.readText() }

val Context.isPortrait get() = resources.configuration.orientation == ORIENTATION_PORTRAIT
val Context.isLandscape get() = !isPortrait
val Context.isTablet get() = resources.configuration.smallestScreenWidthDp >= 600
val Context.isPhone get() = !isTablet
val Context.isScreenWide get() = isTablet || isLandscape

fun Context.drawable(@DrawableRes resource: Int) = getDrawable(this, resource)!!.apply {
    setBounds(0, 0, intrinsicWidth, intrinsicHeight)
}

fun Drawable.createClear(): ColorDrawable = bounds.createClearDrawable()

fun Rect.createClearDrawable(): ColorDrawable = ColorDrawable().also { it.bounds = this }

val Context.isDarkMode: Boolean
    get() = getDefaultNightMode().let {
        if (it.equalsAny(
                MODE_NIGHT_FOLLOW_SYSTEM, MODE_NIGHT_UNSPECIFIED
            )
        ) isSystemDarkMode
        else it == MODE_NIGHT_YES
    }

val Context.isSystemDarkMode
    get() = resources.configuration.uiMode and UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES

@Suppress("DEPRECATION")
fun Context.isServiceRunning(serviceClass: Class<out Service>): Boolean =
    (getSystemService(ACTIVITY_SERVICE) as ActivityManager).getRunningServices(MAX_VALUE)
        .any { serviceClass.name == it.service.className }

val Context.externalFilesDir: File
    get() = getExternalFilesDir(null) ?: getExternalStorageDirectory()

@ColorInt
fun Context.colorFromStyle(@StyleRes style: Int): Int? =
    obtainStyledAttributes(style, intArrayOf(android.R.attr.textColor)).use {
        runCatching { it.getColorOrThrow(0) }.getOrNull()
    }
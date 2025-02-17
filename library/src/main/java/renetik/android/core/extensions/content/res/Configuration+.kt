package renetik.android.core.extensions.content.res

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES

val Configuration.isDarkMode get() = (uiMode and UI_MODE_NIGHT_MASK) == UI_MODE_NIGHT_YES
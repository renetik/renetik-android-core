package renetik.android.core.lang.direction

import renetik.android.core.R
import renetik.android.core.base.CSApplication.Companion.getString

enum class CSVerticalDirection(override val title: String) : CSDirection {
    FromTop(getString(R.string.cs_direction_vertical_from_top)),
    FromBottom(getString(R.string.cs_direction_vertical_from_bottom));

    override val isHorizontal: Boolean = false
}
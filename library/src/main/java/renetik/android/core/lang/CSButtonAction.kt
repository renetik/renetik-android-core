package renetik.android.core.lang

import androidx.annotation.StringRes
import renetik.android.core.base.CSApplication.Companion.app

data class CSButtonAction(val title: String, val onClick: () -> Unit) {
    companion object {
        fun button(title: String, onClick: () -> Unit) =
            CSButtonAction(title, onClick)

        fun CSButtonAction(@StringRes title: Int, onClick: () -> Unit) =
            CSButtonAction(app.getString(title), onClick)
    }
}
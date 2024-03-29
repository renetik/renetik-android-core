package renetik.android.core.lang

import androidx.annotation.LayoutRes

data class CSLayoutRes(@LayoutRes val id: Int) {
    companion object {
        fun layout(@LayoutRes id: Int) = CSLayoutRes(id)

        inline val @receiver:LayoutRes Int.layout get() = CSLayoutRes(this)
    }
}


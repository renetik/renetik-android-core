package renetik.android.core.lang

import androidx.annotation.LayoutRes

data class CSLayoutRes(@LayoutRes val id: Int) {
    companion object {
        fun layout(@LayoutRes id: Int) = CSLayoutRes(id)

        //@get:LayoutRes // TODO: make this check work
        inline val Int.layout get() = CSLayoutRes(this)
    }
}


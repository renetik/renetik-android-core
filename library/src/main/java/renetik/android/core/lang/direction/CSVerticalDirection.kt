package renetik.android.core.lang.direction

enum class CSVerticalDirection : CSDirection {
    FromTop, FromBottom;

    override val isHorizontal: Boolean = false
}
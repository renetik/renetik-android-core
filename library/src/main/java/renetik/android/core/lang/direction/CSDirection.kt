package renetik.android.core.lang.direction

sealed interface CSDirection {
    val isHorizontal: Boolean

    companion object {
        val entries: List<CSDirection> =
            CSHorizontalDirection.entries + CSVerticalDirection.entries
    }
}
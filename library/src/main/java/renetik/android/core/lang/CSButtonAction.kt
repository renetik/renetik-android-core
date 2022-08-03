package renetik.android.core.lang

class CSButtonAction(val title: String, val onClick: () -> Unit) {
    companion object {
        fun button(title: String, onClick: () -> Unit) =
            CSButtonAction(title, onClick)
    }
}
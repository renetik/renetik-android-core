package renetik.android.core.common

import android.graphics.Color.parseColor

data class MaterialColor(val title: String, val hex: String) {
    val colorInt: Int = parseColor(hex)

    companion object {
        val material50 = listOf(
            MaterialColor("Red 50", "#FFEBEE"),
            MaterialColor("Pink 50", "#FCE4EC"),
            MaterialColor("Purple 50", "#F3E5F5"),
            MaterialColor("Deep Purple 50", "#EDE7F6"),
            MaterialColor("Indigo 50", "#E8EAF6"),
            MaterialColor("Blue 50", "#E3F2FD"),
            MaterialColor("Light Blue 50", "#E1F5FE"),
            MaterialColor("Cyan 50", "#E0F7FA"),
            MaterialColor("Teal 50", "#E0F2F1"),
            MaterialColor("Green 50", "#E8F5E9"),
            MaterialColor("Light Green 50", "#F1F8E9"),
            MaterialColor("Lime 50", "#F9FBE7"),
            MaterialColor("Yellow 50", "#FFFDE7"),
            MaterialColor("Amber 50", "#FFF8E1"),
            MaterialColor("Orange 50", "#FFF3E0"),
            MaterialColor("Deep Orange 50", "#FBE9E7"),
            MaterialColor("Brown 50", "#EFEBE9"),
            MaterialColor("Grey 50", "#FAFAFA"),
            MaterialColor("Blue Grey 50", "#ECEFF1")
        )

        val material100 = listOf(
            MaterialColor("Red 100", "#FF8A80"),
            MaterialColor("Pink 100", "#FF80AB"),
            MaterialColor("Purple 100", "#E1BEE7"),
            MaterialColor("Deep Purple 100", "#D1C4E9"),
            MaterialColor("Indigo 100", "#C5CAE9"),
            MaterialColor("Blue 100", "#BBDEFB"),
            MaterialColor("Light Blue 100", "#B3E5FC"),
            MaterialColor("Cyan 100", "#B2EBF2"),
            MaterialColor("Teal 100", "#B9FBC0"),
            MaterialColor("Green 100", "#C5E1A5"),
            MaterialColor("Light Green 100", "#DCE775"),
            MaterialColor("Lime 100", "#F4FF81"),
            MaterialColor("Yellow 100", "#FFFF8D"),
            MaterialColor("Amber 100", "#FFE57F"),
            MaterialColor("Orange 100", "#FFD180"),
            MaterialColor("Deep Orange 100", "#FFAB91"),
            MaterialColor("Brown 100", "#D7CCC8"),
            MaterialColor("Grey 100", "#F5F5F5"),
            MaterialColor("Blue Grey 100", "#CFD8DC")
        )

        val material200 = listOf(
            MaterialColor("Red 200", "#FF5252"),
            MaterialColor("Pink 200", "#FF4081"),
            MaterialColor("Purple 200", "#CE93D8"),
            MaterialColor("Deep Purple 200", "#B39DDB"),
            MaterialColor("Indigo 200", "#9FA8DA"),
            MaterialColor("Blue 200", "#90CAF9"),
            MaterialColor("Light Blue 200", "#81D4FA"),
            MaterialColor("Cyan 200", "#80DEEA"),
            MaterialColor("Teal 200", "#80E2B1"),
            MaterialColor("Green 200", "#AED581"),
            MaterialColor("Light Green 200", "#C0CA33"),
            MaterialColor("Lime 200", "#E1F5FE"),
            MaterialColor("Yellow 200", "#E2F5B5"),
            MaterialColor("Amber 200", "#FFEB3B"),
            MaterialColor("Orange 200", "#FFAB40"),
            MaterialColor("Deep Orange 200", "#FF6E40"),
            MaterialColor("Brown 200", "#BCAAA4"),
            MaterialColor("Grey 200", "#EEEEEE"),
            MaterialColor("Blue Grey 200", "#B0BEC5")
        )

        val material300 = listOf(
            MaterialColor("Red 300", "#E57373"),
            MaterialColor("Pink 300", "#F06292"),
            MaterialColor("Purple 300", "#BA68C8"),
            MaterialColor("Deep Purple 300", "#9575CD"),
            MaterialColor("Indigo 300", "#7986CB"),
            MaterialColor("Blue 300", "#64B5F6"),
            MaterialColor("Light Blue 300", "#4FC3F7"),
            MaterialColor("Cyan 300", "#26C6DA"),
            MaterialColor("Teal 300", "#4DB6AC"),
            MaterialColor("Green 300", "#81C784"),
            MaterialColor("Light Green 300", "#9CCC65"),
            MaterialColor("Lime 300", "#DCE775"),
            MaterialColor("Yellow 300", "#F0F4C3"),
            MaterialColor("Amber 300", "#FFE082"),
            MaterialColor("Orange 300", "#FFB74D"),
            MaterialColor("Deep Orange 300", "#FF8A65"),
            MaterialColor("Brown 300", "#A1887F"),
            MaterialColor("Grey 300", "#E0E0E0"),
            MaterialColor("Blue Grey 300", "#90A4AE")
        )

        val material400 = listOf(
            MaterialColor("Red 400", "#F44336"),
            MaterialColor("Pink 400", "#E91E63"),
            MaterialColor("Purple 400", "#9C27B0"),
            MaterialColor("Deep Purple 400", "#673AB7"),
            MaterialColor("Indigo 400", "#3F51B5"),
            MaterialColor("Blue 400", "#2196F3"),
            MaterialColor("Light Blue 400", "#03A9F4"),
            MaterialColor("Cyan 400", "#00BCD4"),
            MaterialColor("Teal 400", "#009688"),
            MaterialColor("Green 400", "#4CAF50"),
            MaterialColor("Light Green 400", "#8BC34A"),
            MaterialColor("Lime 400", "#CDDC39"),
            MaterialColor("Yellow 400", "#FFEB3B"),
            MaterialColor("Amber 400", "#FFC107"),
            MaterialColor("Orange 400", "#FF9800"),
            MaterialColor("Deep Orange 400", "#FF5722"),
            MaterialColor("Brown 400", "#6D4C41"),
            MaterialColor("Grey 400", "#BDBDBD"),
            MaterialColor("Blue Grey 400", "#607D8B")
        )

        val material500 = listOf(
            MaterialColor("Red 500", "#F44336"),
            MaterialColor("Pink 500", "#E91E63"),
            MaterialColor("Purple 500", "#9C27B0"),
            MaterialColor("Deep Purple 500", "#673AB7"),
            MaterialColor("Indigo 500", "#3F51B5"),
            MaterialColor("Blue 500", "#2196F3"),
            MaterialColor("Light Blue 500", "#03A9F4"),
            MaterialColor("Cyan 500", "#00BCD4"),
            MaterialColor("Teal 500", "#009688"),
            MaterialColor("Green 500", "#4CAF50"),
            MaterialColor("Light Green 500", "#8BC34A"),
            MaterialColor("Lime 500", "#CDDC39"),
            MaterialColor("Yellow 500", "#FFEB3B"),
            MaterialColor("Amber 500", "#FFC107"),
            MaterialColor("Orange 500", "#FF9800"),
            MaterialColor("Deep Orange 500", "#FF5722"),
            MaterialColor("Brown 500", "#795548"),
            MaterialColor("Grey 500", "#9E9E9E"),
            MaterialColor("Blue Grey 500", "#607D8B")
        )

        val material600 = listOf(
            MaterialColor("Red 600", "#E53935"),
            MaterialColor("Pink 600", "#D81B60"),
            MaterialColor("Purple 600", "#8E24AA"),
            MaterialColor("Deep Purple 600", "#5E35B1"),
            MaterialColor("Indigo 600", "#3949AB"),
            MaterialColor("Blue 600", "#1E88E5"),
            MaterialColor("Light Blue 600", "#039BE5"),
            MaterialColor("Cyan 600", "#00ACC1"),
            MaterialColor("Teal 600", "#00897B"),
            MaterialColor("Green 600", "#43A047"),
            MaterialColor("Light Green 600", "#7CB342"),
            MaterialColor("Lime 600", "#C0CA33"),
            MaterialColor("Yellow 600", "#FBC02D"),
            MaterialColor("Amber 600", "#FFB300"),
            MaterialColor("Orange 600", "#FB8C00"),
            MaterialColor("Deep Orange 600", "#F57C00"),
            MaterialColor("Brown 600", "#6D4C41"),
            MaterialColor("Grey 600", "#757575"),
            MaterialColor("Blue Grey 600", "#455A64")
        )

        val material700 = listOf(
            MaterialColor("Red 700", "#D32F2F"),
            MaterialColor("Pink 700", "#C2185B"),
            MaterialColor("Purple 700", "#7B1FA2"),
            MaterialColor("Deep Purple 700", "#512DA8"),
            MaterialColor("Indigo 700", "#303F9F"),
            MaterialColor("Blue 700", "#1976D2"),
            MaterialColor("Light Blue 700", "#0288D1"),
            MaterialColor("Cyan 700", "#0097A7"),
            MaterialColor("Teal 700", "#00796B"),
            MaterialColor("Green 700", "#388E3C"),
            MaterialColor("Light Green 700", "#689F38"),
            MaterialColor("Lime 700", "#AFB42B"),
            MaterialColor("Yellow 700", "#F9A825"),
            MaterialColor("Amber 700", "#FF8F00"),
            MaterialColor("Orange 700", "#F57C00"),
            MaterialColor("Deep Orange 700", "#E64A19"),
            MaterialColor("Brown 700", "#5D4037"),
            MaterialColor("Grey 700", "#616161"),
            MaterialColor("Blue Grey 700", "#37474F")
        )

        val material800 = listOf(
            MaterialColor("Red 800", "#C62828"),
            MaterialColor("Pink 800", "#AD1457"),
            MaterialColor("Purple 800", "#6A1B9A"),
            MaterialColor("Deep Purple 800", "#4527A0"),
            MaterialColor("Indigo 800", "#283593"),
            MaterialColor("Blue 800", "#1565C0"),
            MaterialColor("Light Blue 800", "#0277BD"),
            MaterialColor("Cyan 800", "#00838F"),
            MaterialColor("Teal 800", "#00695C"),
            MaterialColor("Green 800", "#2C6B2F"),
            MaterialColor("Light Green 800", "#558B2F"),
            MaterialColor("Lime 800", "#9E9D24"),
            MaterialColor("Yellow 800", "#F57F17"),
            MaterialColor("Amber 800", "#FF6F00"),
            MaterialColor("Orange 800", "#EF6C00"),
            MaterialColor("Deep Orange 800", "#DD5600"),
            MaterialColor("Brown 800", "#4E342E"),
            MaterialColor("Grey 800", "#424242"),
            MaterialColor("Blue Grey 800", "#263238")
        )

        val material900 = listOf(
            MaterialColor("Red 900", "#B71C1C"),
            MaterialColor("Pink 900", "#880E4F"),
            MaterialColor("Purple 900", "#4A148C"),
            MaterialColor("Deep Purple 900", "#311B92"),
            MaterialColor("Indigo 900", "#1A237E"),
            MaterialColor("Blue 900", "#0D47A1"),
            MaterialColor("Light Blue 900", "#01579B"),
            MaterialColor("Cyan 900", "#006064"),
            MaterialColor("Teal 900", "#004D40"),
            MaterialColor("Green 900", "#003D33"),
            MaterialColor("Light Green 900", "#33691E"),
            MaterialColor("Lime 900", "#827717"),
            MaterialColor("Yellow 900", "#F57F17"),
            MaterialColor("Amber 900", "#FF6F00"),
            MaterialColor("Orange 900", "#E65100"),
            MaterialColor("Deep Orange 900", "#BF360C"),
            MaterialColor("Brown 900", "#3E2723"),
            MaterialColor("Grey 900", "#212121"),
            MaterialColor("Blue Grey 900", "#102027")
        )

        val all = material100 + material200 + material300 + material400 +
                material500 + material600 + material700 + material800 + material900
    }
}
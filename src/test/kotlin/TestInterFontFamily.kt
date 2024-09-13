import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

private val InterFontFamily =
    FontFamily(
        Font(resource = "test-fonts/inter/Inter-Thin.ttf", weight = FontWeight.Thin),
        Font(resource = "test-fonts/inter/Inter-ThinItalic.ttf", weight = FontWeight.Thin, style = FontStyle.Italic),
        Font(resource = "test-fonts/inter/Inter-ExtraLight.ttf", weight = FontWeight.ExtraLight),
        Font(
            resource = "test-fonts/inter/Inter-ExtraLightItalic.ttf",
            weight = FontWeight.ExtraLight,
            style = FontStyle.Italic,
        ),
        Font(resource = "test-fonts/inter/Inter-Light.ttf", weight = FontWeight.Light),
        Font(resource = "test-fonts/inter/Inter-LightItalic.ttf", weight = FontWeight.Light, style = FontStyle.Italic),
        Font(resource = "test-fonts/inter/Inter-Regular.ttf", weight = FontWeight.Normal),
        Font(resource = "test-fonts/inter/Inter-Italic.ttf", weight = FontWeight.Normal, style = FontStyle.Italic),
        Font(resource = "test-fonts/inter/Inter-Medium.ttf", weight = FontWeight.Medium),
        Font(
            resource = "test-fonts/inter/Inter-MediumItalic.ttf",
            weight = FontWeight.Medium,
            style = FontStyle.Italic
        ),
        Font(resource = "test-fonts/inter/Inter-SemiBold.ttf", weight = FontWeight.SemiBold),
        Font(
            resource = "test-fonts/inter/Inter-SemiBoldItalic.ttf",
            weight = FontWeight.SemiBold,
            style = FontStyle.Italic,
        ),
        Font(resource = "test-fonts/inter/Inter-Bold.ttf", weight = FontWeight.Bold),
        Font(resource = "test-fonts/inter/Inter-BoldItalic.ttf", weight = FontWeight.Bold, style = FontStyle.Italic),
        Font(resource = "test-fonts/inter/Inter-ExtraBold.ttf", weight = FontWeight.ExtraBold),
        Font(
            resource = "test-fonts/inter/Inter-ExtraBoldItalic.ttf",
            weight = FontWeight.ExtraBold,
            style = FontStyle.Italic,
        ),
        Font(resource = "test-fonts/inter/Inter-Black.ttf", weight = FontWeight.Black),
        Font(resource = "test-fonts/inter/Inter-BlackItalic.ttf", weight = FontWeight.Black, style = FontStyle.Italic),
    )

internal val FontFamily.Companion.InterForTests: FontFamily
    get() = InterFontFamily

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font

private val JetBrainsMonoFontFamily =
  FontFamily(
    Font(resource = "test-fonts/jetbrains-mono/JetBrainsMono-Thin.ttf", weight = FontWeight.Thin),
    Font(
      resource = "test-fonts/jetbrains-mono/JetBrainsMono-ThinItalic.ttf",
      weight = FontWeight.Thin,
      style = FontStyle.Italic,
    ),
    Font(
      resource = "test-fonts/jetbrains-mono/JetBrainsMono-ExtraLight.ttf",
      weight = FontWeight.ExtraLight,
    ),
    Font(
      resource = "test-fonts/jetbrains-mono/JetBrainsMono-ExtraLightItalic.ttf",
      weight = FontWeight.ExtraLight,
      style = FontStyle.Italic,
    ),
    Font(resource = "test-fonts/jetbrains-mono/JetBrainsMono-Light.ttf", weight = FontWeight.Light),
    Font(
      resource = "test-fonts/jetbrains-mono/JetBrainsMono-LightItalic.ttf",
      weight = FontWeight.Light,
      style = FontStyle.Italic,
    ),
    Font(
      resource = "test-fonts/jetbrains-mono/JetBrainsMono-Regular.ttf",
      weight = FontWeight.Normal,
    ),
    Font(
      resource = "test-fonts/jetbrains-mono/JetBrainsMono-Italic.ttf",
      weight = FontWeight.Normal,
      style = FontStyle.Italic,
    ),
    Font(
      resource = "test-fonts/jetbrains-mono/JetBrainsMono-Medium.ttf",
      weight = FontWeight.Medium,
    ),
    Font(
      resource = "test-fonts/jetbrains-mono/JetBrainsMono-MediumItalic.ttf",
      weight = FontWeight.Medium,
      style = FontStyle.Italic,
    ),
    Font(
      resource = "test-fonts/jetbrains-mono/JetBrainsMono-SemiBold.ttf",
      weight = FontWeight.SemiBold,
    ),
    Font(
      resource = "test-fonts/jetbrains-mono/JetBrainsMono-SemiBoldItalic.ttf",
      weight = FontWeight.SemiBold,
      style = FontStyle.Italic,
    ),
    Font(resource = "test-fonts/jetbrains-mono/JetBrainsMono-Bold.ttf", weight = FontWeight.Bold),
    Font(
      resource = "test-fonts/jetbrains-mono/JetBrainsMono-BoldItalic.ttf",
      weight = FontWeight.Bold,
      style = FontStyle.Italic,
    ),
    Font(
      resource = "test-fonts/jetbrains-mono/JetBrainsMono-ExtraBold.ttf",
      weight = FontWeight.ExtraBold,
    ),
    Font(
      resource = "test-fonts/jetbrains-mono/JetBrainsMono-ExtraBoldItalic.ttf",
      weight = FontWeight.ExtraBold,
      style = FontStyle.Italic,
    ),
    Font(resource = "test-fonts/jetbrains-mono/JetBrainsMono-Black.ttf", weight = FontWeight.Black),
    Font(
      resource = "test-fonts/jetbrains-mono/JetBrainsMono-BlackItalic.ttf",
      weight = FontWeight.Black,
      style = FontStyle.Italic,
    ),
  )

internal val FontFamily.Companion.JetBrainsMonoForTests: FontFamily
  get() = JetBrainsMonoFontFamily

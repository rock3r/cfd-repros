import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.intui.standalone.theme.createDefaultTextStyle
import org.jetbrains.jewel.intui.standalone.theme.createEditorTextStyle
import org.jetbrains.jewel.intui.standalone.theme.dark
import org.jetbrains.jewel.intui.standalone.theme.darkThemeDefinition
import org.jetbrains.jewel.intui.standalone.theme.light
import org.jetbrains.jewel.intui.standalone.theme.lightThemeDefinition
import org.jetbrains.jewel.ui.ComponentStyling

@Composable
fun TestTheme(darkMode: Boolean = false, content: @Composable () -> Unit) {
    val defaultTextStyle = JewelTheme.createDefaultTextStyle(fontFamily = FontFamily.InterForTests)
    val editorTextStyle =
        JewelTheme.createEditorTextStyle(fontFamily = FontFamily.JetBrainsMonoForTests)

    val themeDefinition =
        if (darkMode) {
            JewelTheme.darkThemeDefinition(
                defaultTextStyle = defaultTextStyle,
                editorTextStyle = editorTextStyle,
            )
        } else {
            JewelTheme.lightThemeDefinition(
                defaultTextStyle = defaultTextStyle,
                editorTextStyle = editorTextStyle,
            )
        }

    val componentStyling = if (darkMode) {
        ComponentStyling.dark()
    } else {
        ComponentStyling.light()
    }

    IntUiTheme(themeDefinition, componentStyling, true, content)
}

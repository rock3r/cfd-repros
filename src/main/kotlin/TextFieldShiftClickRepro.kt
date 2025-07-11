import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.Enter
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.ui.component.TextArea

fun main() = singleWindowApplication {
    IntUiTheme {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            val textAreaState = rememberTextFieldState(
                """This is a test to reproduce the issue where pressing Shift+Click 
                    |in a TextArea does extend selection, 
                    |place the cursor somewhere than Shift+Click
                    |somewhere else!""".trimMargin()
            )
            TextArea(
                textAreaState,
                Modifier
                    .heightIn(50.dp, 150.dp)
                    .fillMaxWidth()
            )
        }
    }
}

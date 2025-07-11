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
            val textAreaState = rememberTextFieldState()
            TextArea(
                textAreaState,
                Modifier
                    .heightIn(50.dp, 150.dp)
                    .fillMaxWidth()
                    .onPreviewKeyEvent { keyEvent ->
                        // We only want to handle KeyDown events
                        if (keyEvent.type == KeyEventType.KeyDown) {
                            if (keyEvent.key == Enter && keyEvent.isShiftPressed) {
                                // SHIFT + ENTER -> Insert new line
                                textAreaState.edit {
                                    val start = selection.start
                                    val end = selection.end
                                    // Replace the selected text (or just insert) with "\n"
                                    replace(start, end, "\n")

                                    // Move the cursor just after the inserted newline
                                    placeCursorAfterCharAt(start.coerceIn(0..textAreaState.text.length))
                                }
                                return@onPreviewKeyEvent true
                            } else if (keyEvent.key == Enter) {
                                // ENTER -> Trigger your callback (no new line)
                                print("submit: ${textAreaState.text}")
                                return@onPreviewKeyEvent true
                            }
                        }
                        return@onPreviewKeyEvent false
                    },
            )
        }
    }
}

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.ui.component.OutlinedButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextArea

fun main() = singleWindowApplication {
    IntUiTheme {
        Column(Modifier.fillMaxSize().background(JewelTheme.globalColors.panelBackground).padding(16.dp)) {
            val state = rememberTextFieldState("Hello, this is some random text.")
            val typingLog = rememberTextFieldState()

            BasicTextField(
                state,
                Modifier.size(250.dp, 80.dp).background(Color.White).border(1.dp, Color.Gray).padding(1.dp)
                    .onPreviewKeyEvent { event ->
                        if (event.type != KeyEventType.KeyDown) return@onPreviewKeyEvent false

                        val logEntry = buildString {
                            if (event.isMetaPressed) append("META+")
                            if (event.isCtrlPressed) append("CTRL+")
                            if (event.isAltPressed) append("ALT+")
                            if (event.isShiftPressed) append("SHIFT+")
                            appendLine(java.awt.event.KeyEvent.getKeyText(event.key.nativeKeyCode))
                        }
                        typingLog.edit {
                            insert(0, logEntry)
                        }
                        false
                    },
            )

            Spacer(Modifier.height(20.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                TextArea(typingLog, readOnly = true, undecorated = true, modifier = Modifier.width(250.dp))
                OutlinedButton({typingLog.clearText()}) {
                    Text("Clear log")
                }
            }
        }
    }
}

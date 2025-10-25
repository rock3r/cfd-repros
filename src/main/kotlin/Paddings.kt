import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import org.jetbrains.jewel.foundation.ExperimentalJewelApi
import org.jetbrains.jewel.foundation.theme.LocalThemeInstanceUuid
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.ui.component.OutlinedButton
import org.jetbrains.jewel.ui.component.Text
import java.util.UUID

@OptIn(ExperimentalJewelApi::class)
fun main() = singleWindowApplication {
    CompositionLocalProvider(LocalThemeInstanceUuid provides UUID.randomUUID()) {
        IntUiTheme {
            Column(Modifier.border(1.dp, Color.Red).padding(24.dp).border(1.dp, Color.Green)) {
                OutlinedButton({}) { Text("I am a button") }

                Box(Modifier.border(1.dp, Color.Magenta).padding(8.dp).border(1.dp, Color.Green)) {
                    OutlinedButton({}) { Text("I am a button") }
                }
            }
        }
    }
}

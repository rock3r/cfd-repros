import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.ui.component.TextArea

fun main() = singleWindowApplication {
    IntUiTheme {
        Column(Modifier.fillMaxSize().padding(16.dp)) {
            val state = rememberTextFieldState()
            val scrollState = rememberScrollState()
            TextArea(
                state,
                Modifier.heightIn(50.dp, 150.dp).fillMaxWidth(),
                scrollState = scrollState,
            )
            Spacer(Modifier.height(16.dp))
            BasicText("Value -> MaxValue: ${scrollState.value} -> ${scrollState.maxValue}")
            BasicText("CanScrollBackward: ${scrollState.canScrollBackward}")
            BasicText("CanScrollForward: ${scrollState.canScrollForward}")
        }
    }
}

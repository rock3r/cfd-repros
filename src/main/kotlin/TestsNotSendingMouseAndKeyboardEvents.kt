import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.testTag
import org.jetbrains.jewel.foundation.modifier.onHover
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ThisDoesNotReceiveMouseAndKeyboardEvents() {
    var currentSelected by remember { mutableIntStateOf(0) }
    var hovered by remember { mutableStateOf(false) }
    Box(
        Modifier.fillMaxSize()
            .handleKeys(currentSelected, 10) { currentSelected = it.takeIf { it < 10 } ?: 0 }
            .testTag("target")
            .onHover { hovered = it }
    ) {
        Text("Whatever $currentSelected${if (hovered) " hovered" else ""}", Modifier.testTag("label"))
    }
}

private fun Modifier.handleKeys(
    currentSelected: Int,
    entriesCount: Int,
    onChangeSelection: (Int) -> Unit,
) = onPreviewKeyEvent { event ->
    if (event.type != KeyEventType.KeyDown) return@onPreviewKeyEvent false

    val key = event.key
    when (key) {
        Key.DirectionUp -> {
            if (currentSelected > 0) onChangeSelection(currentSelected - 1)
            else onChangeSelection(entriesCount - 1)
        }

        Key.DirectionDown -> {
            if (currentSelected < entriesCount - 1) onChangeSelection(currentSelected + 1)
            else onChangeSelection(0)
        }

        Key.MoveHome -> onChangeSelection(0)
        Key.MoveEnd -> onChangeSelection(entriesCount - 1)
        else -> return@onPreviewKeyEvent false
    }

    true
}

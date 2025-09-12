@file:Suppress("UnstableApiUsage")
@file:OptIn(ExperimentalJewelApi::class)

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import org.jetbrains.jewel.foundation.ExperimentalJewelApi
import org.jetbrains.jewel.foundation.theme.LocalThemeInstanceUuid
import org.jetbrains.jewel.intui.markdown.standalone.ProvideMarkdownStyling
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.markdown.Markdown
import org.jetbrains.jewel.ui.Orientation
import org.jetbrains.jewel.ui.component.Divider
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.VerticallyScrollableContainer
import java.util.UUID

fun main() = singleWindowApplication {
    CompositionLocalProvider(LocalThemeInstanceUuid provides UUID.randomUUID()) {
    IntUiTheme {
        ProvideMarkdownStyling {
            val listState = rememberLazyListState()
            VerticallyScrollableContainer(listState as ScrollableState) {
                LazyColumn(
                    Modifier.fillMaxSize().padding(16.dp),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(100) { index ->
                        if (index % 2 == 0) {
                            SelectionContainer { ItemContent(index) }
                        } else {
                            ItemContent(index)
                        }
                    }
                }
            }
        }
    }
    }
}

@Composable
private fun ItemContent(index: Int) {
    Column {
        Text("Item $index${if (index % 2 == 0) " (selectable/clickable)" else ""}")
        Markdown("This is an example Markdown bit.\n\n```kotlin\n$SAMPLE_CODE\n```")
        if (index < 99) {
            Divider(Orientation.Horizontal, Modifier.fillMaxWidth())
        }
    }
}

private val SAMPLE_CODE =
    """
  override fun onRequestPermissionsResult(
      requestCode: Int,
      permissions: Array<String>,
      grantResults: IntArray
  ) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
      when (requestCode) {
          YOUR_PERMISSION_REQUEST_CODE -> {
              // If request is cancelled, the result arrays are empty.
              if ((grantResults.isNotEmpty() &&
                          grantResults[0] == PackageManager.PERMISSION_GRANTED)
              ) {
                  // Permission is granted. Continue the action or workflow
                  // in your app.
              } else {
                  // Explain to the user that the feature is unavailable because
                  // the feature requires a permission that the user has denied.
                  // At the same time, respect the user's decision. Don't link to
                  // system settings in an effort to convince the user to change
                  // their decision.
              }
              return
          }
          // Add other 'when' lines to check for other
          // permissions this app might request.
          else -> {
              // Ignore all other requests.
          }
      }
  }
  """
        .trimIndent()

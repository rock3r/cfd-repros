import androidx.compose.ui.InternalComposeUiApi
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performKeyPress
import androidx.compose.ui.test.performMouseInput
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.junit.Rule
import org.junit.Test

class TestsNotSendingMouseAndKeyboardEventsTest {
  @get:Rule
  val rule = createComposeRule()

  @OptIn(InternalComposeUiApi::class)
  @Test
  fun `should send a key event`() {
    rule.setContent { IntUiTheme { ThisDoesNotReceiveMouseAndKeyboardEvents() } }

    rule.onNodeWithTag("label").assertIsDisplayed().assertTextEquals("Whatever 0")

    rule
      .onNodeWithTag("target")
      .assertIsDisplayed()
      .performKeyPress(KeyEvent(Key.DirectionDown, KeyEventType.KeyDown))

    rule.onNodeWithTag("label").assertIsDisplayed().assertTextEquals("Whatever 1")
  }

  @OptIn(ExperimentalTestApi::class)
  @Test
  fun `should send a mouse event`() {
    rule.setContent { IntUiTheme { ThisDoesNotReceiveMouseAndKeyboardEvents() } }

    rule.onNodeWithTag("label").assertIsDisplayed().assertTextEquals("Whatever 0")

    rule.onNodeWithTag("target").assertIsDisplayed().performMouseInput {
      enter()
      moveBy(Offset(1f, 1f))
    }

    rule.onNodeWithTag("label").assertIsDisplayed().assertTextEquals("Whatever 0 hovered")
  }
}

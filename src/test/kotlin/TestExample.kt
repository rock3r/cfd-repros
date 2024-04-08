import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.ui.component.DefaultButton
import org.jetbrains.jewel.ui.component.OutlinedButton
import org.jetbrains.jewel.ui.component.Text
import org.junit.Rule
import org.junit.Test

class TestExample {

  @get:Rule
  val rule = createComposeRule()

  @Test
  fun `should not hang while testing`() {
    rule.setContent {
      IntUiTheme {
        MyPage(
          canGoForward = true,
          canGoBackwards = true,
          isLastPage = false,
          onBackClick = {},
          onNextClick = {}
        )
      }
    }

    rule.onNodeWithTag("LayoutTags.Scaffold.nextButton").assertExists()
      .assertTextEquals("next")
  }
}

@Composable
internal fun MyPage(
  canGoForward: Boolean,
  canGoBackwards: Boolean,
  isLastPage: Boolean,
  onBackClick: () -> Unit,
  onNextClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  PageScaffold(
    canGoBackwards,
    canGoForward,
    isLastPage,
    onBackClick,
    onNextClick,
    modifier,
  ) {
    Box {
      Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
          text = "title",
          fontWeight = FontWeight.Bold,
          modifier = Modifier.testTag("LayoutTags.MyPage.title"),
        )

        Text(
          text =
          "blurb",
          modifier = Modifier.testTag("LayoutTags.MyPage.label"),
        )
      }
    }
  }
}

@Composable
private fun PageScaffold(
  canGoBackwards: Boolean,
  canGoForward: Boolean,
  isLastPage: Boolean,
  onBackClick: () -> Unit,
  onNextClick: () -> Unit,
  modifier: Modifier = Modifier,
  pageContent: @Composable () -> Unit,
) {
  Column(modifier, verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)) {
    Image(
      painterResource("cat.jpg"),
      contentDescription = null,
      modifier =
      Modifier.align(Alignment.CenterHorizontally).testTag("LayoutTags.Scaffold.image"),
    )

    pageContent()

    Row(Modifier.align(Alignment.End), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
      if (canGoBackwards) {
        OutlinedButton(onBackClick, Modifier.testTag("LayoutTags.Scaffold.backButton")) {
          Text("back")
        }
      }

      if (isLastPage) {
        DefaultButton(
          onNextClick,
          enabled = canGoForward,
          modifier = Modifier.testTag("LayoutTags.Scaffold.finishButton"),
        ) {
          Text("Finish")
        }
      } else {
        DefaultButton(
          onNextClick,
          enabled = canGoForward,
          modifier = Modifier.testTag("LayoutTags.Scaffold.nextButton"),
        ) {
          Text("next")
        }
      }
    }
  }
}

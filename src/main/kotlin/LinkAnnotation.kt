import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication

fun main() = singleWindowApplication {
  Column(Modifier.padding(16.dp)) {
    val styles = remember {
      TextLinkStyles(
        style = SpanStyle(color = Color.Blue),
        focusedStyle = SpanStyle(color = Color.Red),
        hoveredStyle = SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline),
        pressedStyle = SpanStyle(color = Color.Red, textDecoration = TextDecoration.Underline),
      )
    }
    val text = remember {
      buildAnnotatedString {
        append("Hello, banana, this is a perfectly normal ")
        pushLink(LinkAnnotation.Url("https://www.google.com", styles))
        append("but quite long link to Google")
        pop()
        append(", and you can have you own link, too, if you want. ")
        pushLink(LinkAnnotation.Url("https://www.google.com/search?q=whatever", styles))
        append("There is another long link here")
        pop()
        append(", I dunno, whatever.")
      }
    }
    BasicText(text)

    Spacer(Modifier.height(16.dp))

    val interactionSource = remember { MutableInteractionSource() }
    var borderColor by remember { mutableStateOf(Color.Gray) }
    Box(
      Modifier.border(1.dp, borderColor)
        .clickable(interactionSource, indication = null) {}
        .size(150.dp, 20.dp)
    ) {
      BasicText("I am clickable", Modifier.align(Alignment.Center))
    }

    LaunchedEffect(interactionSource) {
      interactionSource.interactions.collect { interaction ->
        when (interaction) {
          is FocusInteraction.Unfocus -> borderColor = Color.Gray
          is FocusInteraction.Focus -> borderColor = Color.Red
        }
      }
    }
  }
}

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import org.jetbrains.jewel.foundation.ExperimentalJewelApi
import org.jetbrains.jewel.intui.markdown.standalone.ProvideMarkdownStyling
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.markdown.Markdown
import org.jetbrains.jewel.ui.component.OutlinedButton
import org.jetbrains.jewel.ui.component.Text

@OptIn(ExperimentalJewelApi::class)
fun main() = singleWindowApplication {
    IntUiTheme {
        ProvideMarkdownStyling {
            SelectionContainer(Modifier.padding(24.dp)) {
                val annotatedString = remember {
                    buildAnnotatedString {
                        append("I love bananas, as they are yellow, but I have a ")
                        withLink(
                            LinkAnnotation.Url(
                                "https://google.com",
                                TextLinkStyles(
                                    style = SpanStyle(color = Color.Blue),
                                    focusedStyle = SpanStyle(color = Color.Blue, background = Color.Yellow),
                                    hoveredStyle =
                                        SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline),
                                    pressedStyle =
                                        SpanStyle(
                                            color = Color.Blue,
                                            textDecoration = TextDecoration.Underline,
                                            background = Color.Yellow,
                                        ),
                                ),
                            )
                        ) {
                            append("very looooong link that spans more than one line")
                        }
                        append(" and thus I cause issues...")
                    }
                }
                Column {
                    var count by remember { mutableIntStateOf(0) }
                    //        Text("I love bananas! $count")
                    //        Text(annotatedString)
                    //        Text("I love bananas again")
                    Markdown(
                        "I love bananas! $count.\n\n" +
                                "I love bananas, as they are yellow, but I have a " +
                                "[very looooong link that spans more than one line](https://google.com) and thus I cause issues...\n\n" +
                                "I love bananas again"
                    )
                    OutlinedButton({ count++ }) { Text("Increment bananas!") }
                    OutlinedButton({ count-- }) { Text("Decrement bananas!") }
                }
            }
        }
    }
}

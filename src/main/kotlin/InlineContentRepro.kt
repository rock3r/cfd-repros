import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import coil3.compose.LocalPlatformContext
import org.jetbrains.jewel.foundation.ExperimentalJewelApi
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.intui.markdown.standalone.ProvideMarkdownStyling
import org.jetbrains.jewel.intui.markdown.standalone.dark
import org.jetbrains.jewel.intui.markdown.standalone.light
import org.jetbrains.jewel.intui.markdown.standalone.styling.dark
import org.jetbrains.jewel.intui.markdown.standalone.styling.light
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.markdown.Markdown
import org.jetbrains.jewel.markdown.extensions.images.Coil3ImageRendererExtension
import org.jetbrains.jewel.markdown.rendering.MarkdownBlockRenderer
import org.jetbrains.jewel.markdown.rendering.MarkdownStyling
import org.jetbrains.jewel.ui.component.Icon
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.icons.AllIconsKeys

@Suppress("UnstableApiUsage")
@OptIn(ExperimentalJewelApi::class)
fun main() = singleWindowApplication {
    IntUiTheme {
        SelectionContainer {
            Column(Modifier.fillMaxSize().background(JewelTheme.globalColors.panelBackground).padding(16.dp)) {
                val content = remember {
                    buildAnnotatedString {
                        append("Hello hello I am an annotated string. I have ")
                        withLink(LinkAnnotation.Url("https://jewel-ui.dev")) {
                            append("links")
                        }
                        appendLine("!")
                        appendLine()
                        append("I also have inline content, such as ")
                        appendInlineContent("icon1")
                        append(" and other amusing things I cannot wait to tell you about!")
                    }
                }

                val density = LocalDensity.current
                Text(content, inlineContent = remember {
                    mapOf("icon1" to InlineTextContent(with(density) {
                        Placeholder(16.dp.toSp(), 16.dp.toSp(), PlaceholderVerticalAlign.Center)
                    }, { Icon(AllIconsKeys.General.Settings, contentDescription = null) }))
                })

                Spacer(Modifier.height(32.dp))

                val coilContext = LocalPlatformContext.current
                val coil3ImageRendererExtension =
                    remember(coilContext) { Coil3ImageRendererExtension.withDefaultLoader(coilContext) }
                val isDark = JewelTheme.isDark
                val instanceUuid = JewelTheme.instanceUuid

                val markdownStyling =
                    remember(instanceUuid) { if (isDark) MarkdownStyling.dark() else MarkdownStyling.light() }
                val blockRenderer =
                    remember(markdownStyling) {
                        if (isDark) {
                            MarkdownBlockRenderer.dark(
                                styling = markdownStyling,
                                rendererExtensions = listOf(coil3ImageRendererExtension),
                            )
                        } else {
                            MarkdownBlockRenderer.light(
                                styling = markdownStyling,
                                rendererExtensions = listOf(coil3ImageRendererExtension),
                            )
                        }
                    }

                ProvideMarkdownStyling(markdownStyling = markdownStyling, markdownBlockRenderer = blockRenderer) {
                    Markdown(
                        "This is **Markdown**! ![image](https://resources.jetbrains.com/storage/products/company/brand/logos/Kotlin_icon.svg)",
                        blockRenderer = blockRenderer
                    )
                }
            }
        }
    }
}

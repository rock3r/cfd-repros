import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.v2.ScrollbarAdapter
import androidx.compose.foundation.v2.maxScrollOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.intellij.openapi.project.Project
import org.jetbrains.jewel.ui.component.IconActionButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.VerticalScrollbar
import org.jetbrains.jewel.ui.icons.AllIconsKeys
import org.junit.Rule
import org.junit.Test

class TestExample {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun `should not hang while testing`() {
        val fooProvider: FooProvider = TestFooProvider()
        rule.setContent {
            TestTheme {
                MyRoot(fooProvider, Modifier.fillMaxSize())
            }
        }

        rule.onNodeWithTag("test123").assertDoesNotExist()
    }
}

@Composable
private fun MyRoot(fooProvider: FooProvider, modifier: Modifier = Modifier) {
    val items = remember { listOf("One", "Two", "Three") }
    val listState = rememberLazyListState()
    val scrollbarAdapter = rememberScrollbarAdapter(listState)
    Box(modifier) {
        LazyColumn(
            modifier = Modifier.verticalScrim(scrollbarAdapter, Color.Red)
                .padding(end = 16.dp),
            state = listState
        ) {
            items(items, key = { it }) { Item(it, fooProvider) }
        }

        VerticalScrollbar(
            scrollbarAdapter,
            Modifier.align(Alignment.TopEnd)
                .fillMaxHeight()
                .padding(top = 2.dp, end = 2.dp, bottom = 2.dp)
        )
    }
}

private fun Modifier.verticalScrim(
    scrollbarAdapter: ScrollbarAdapter,
    scrimColor: Color,
    scrimHeight: Dp = 20.dp,
) =
    verticalScrimImpl(
        scrollbarAdapter.canScrollForward,
        scrollbarAdapter.canScrollBackward,
        scrollbarAdapter.scrollOffset.toFloat(),
        scrollbarAdapter.maxScrollOffset.toFloat(),
        scrimColor,
        scrimHeight,
    )

private val ScrollbarAdapter.canScrollForward
    get() = scrollOffset < maxScrollOffset

private val ScrollbarAdapter.canScrollBackward
    get() = scrollOffset > 0.0

private fun Modifier.verticalScrimImpl(
    canScrollForward: Boolean,
    canScrollBackward: Boolean,
    scrollOffset: Float,
    maxScrollOffset: Float,
    scrimColor: Color,
    scrimHeight: Dp = 20.dp,
) =
    drawWithContent {
        drawContent()

        val scrimHeightPx = scrimHeight.toPx()
        if (canScrollForward) {
            val remainingScroll = maxScrollOffset - scrollOffset
            val adjustedScrimHeight =
                if (remainingScroll > scrimHeightPx) {
                    scrimHeightPx
                } else {
                    (remainingScroll / scrimHeightPx) * scrimHeightPx
                }

            val scrimBrush =
                Brush.verticalGradient(
                    colors = listOf(scrimColor.copy(alpha = 0f), scrimColor),
                    startY = size.height - adjustedScrimHeight,
                    endY = size.height,
                )

            drawRect(
                scrimBrush,
                topLeft = Offset(0f, size.height - adjustedScrimHeight),
                size = Size(size.width, adjustedScrimHeight),
            )
        }

        if (canScrollBackward) {
            val adjustedScrimHeight =
                if (scrollOffset > scrimHeightPx) {
                    scrimHeightPx
                } else {
                    (scrollOffset / scrimHeightPx) * scrimHeightPx
                }

            val scrimBrush =
                Brush.verticalGradient(
                    colors = listOf(scrimColor, scrimColor.copy(alpha = 0f)),
                    startY = 0f,
                    endY = adjustedScrimHeight,
                )

            drawRect(scrimBrush, topLeft = Offset(0f, 0f), size = Size(size.width, adjustedScrimHeight))
        }
    }

@Composable
private fun Item(item: String, fooProvider: FooProvider, modifier: Modifier = Modifier) {
    Item(item, modifier) { println(fooProvider.getFoo()) }
}

@Composable
private fun Item(item: String, modifier: Modifier = Modifier, onAction: () -> Unit) {
    Column {
        Text(item)

        IconActionButton(
            AllIconsKeys.Actions.Dump,
            contentDescription = null,
            onClick = onAction,
            modifier = modifier,
        )
    }
}

private interface FooProvider {
    fun getFoo(): Project
}

private class TestFooProvider : FooProvider {
    override fun getFoo(): Project =
        throw UnsupportedOperationException("Not supported on this fake instance")

    override fun equals(other: Any?): Boolean {
        throw UnsupportedOperationException("Equals not supported on this fake instance")
    }

    override fun hashCode(): Int {
        throw UnsupportedOperationException("hashCode not supported on this fake instance")
    }
}

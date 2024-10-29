@file:OptIn(ExperimentalSharedTransitionApi::class)

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import kotlinx.coroutines.delay
import org.jetbrains.jewel.ui.component.painterResource
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalComposeUiApi::class)
fun main() =
  singleWindowApplication(
    title = "adaptive week",
  ) {
    var sizeMultiplier by remember { mutableFloatStateOf(1f) }
    var windowSize by remember { mutableStateOf(IntSize.Zero) }
    val windowWidth by
    animateDpAsState(
      targetValue = with(LocalDensity.current) { (sizeMultiplier * windowSize.width).toDp() },
      animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
    )

    LaunchedEffect(Unit) {
      while (true) {
        sizeMultiplier = 1f
        delay(1200.milliseconds)
        sizeMultiplier = .6f
        delay(1200.milliseconds)
        sizeMultiplier = .35f
        delay(1200.milliseconds)
        sizeMultiplier = .5f
        delay(1200.milliseconds)
        sizeMultiplier = .75f
        delay(1200.milliseconds)
      }
    }

    SharedTransitionLayout(
      Modifier
        .fillMaxSize()
        .onSizeChanged { windowSize = it }
        .background(Color(0xFF282238))
        .padding(16.dp)
    ) {
      Box(Modifier.width(windowWidth)) {
        AnimatedContent(
          windowWidth,
          Modifier
            .fillMaxSize()
            .padding(end = 15.dp)
            .border(4.dp, Color(0xFFE6F284), RoundedCornerShape(10.dp)),
          transitionSpec = { EnterTransition.None togetherWith ExitTransition.None },
        ) { availableWidth ->
          when {
            availableWidth < 600.dp ->
              TwoColumnsLayout(
                Modifier.fillMaxWidth(),
                this@AnimatedContent,
                this@SharedTransitionLayout,
              )

            else ->
              ThreeColumnsLayout(
                Modifier.fillMaxWidth(),
                this@AnimatedContent,
                this@SharedTransitionLayout,
              )
          }
        }

        val cursor = painterResource("resizeleftright.svg")
        Image(cursor, null, Modifier.align(Alignment.CenterEnd))
      }
    }
  }

private const val SpringSpatialDamping = 0.75f
private const val SpringSpatialStiffness = 1200.0f

private val springyBoundsTransform = BoundsTransform { _, _ ->
  spring(SpringSpatialDamping, SpringSpatialStiffness)
}

@Composable
fun TwoColumnsLayout(
  modifier: Modifier,
  animatedContentScope: AnimatedContentScope,
  sharedTransitionScope: SharedTransitionScope,
) {
  Row(modifier.padding(16.dp)) {
    with(sharedTransitionScope) {
      Column(Modifier.weight(.25f)) {
        Box(
          Modifier
            .sharedElement(
              rememberSharedContentState("red"),
              animatedContentScope,
              zIndexInOverlay = 1f,
              boundsTransform = springyBoundsTransform,
            )
            .fillMaxWidth()
            .weight(1f)
            .background(Color(0x30C4B4ED), RoundedCornerShape(8.dp))
            .border(4.dp, Color(0xFFC4B4ED), RoundedCornerShape(8.dp))
        )

        Spacer(Modifier.height(8.dp))

        Box(
          Modifier
            .sharedElement(
              rememberSharedContentState("green"),
              animatedContentScope,
              zIndexInOverlay = 2f,
              boundsTransform = springyBoundsTransform,
            )
            .fillMaxWidth()
            .weight(1f)
            .background(Color(0x30C4B4ED), RoundedCornerShape(8.dp))
            .border(4.dp, Color(0xFFC4B4ED), RoundedCornerShape(8.dp))
        )
      }

      Spacer(Modifier.width(8.dp))

      Box(
        Modifier
          .sharedElement(
            rememberSharedContentState("blue"),
            animatedContentScope,
            zIndexInOverlay = 0f,
            boundsTransform = springyBoundsTransform,
          )
          .weight(.5f)
          .fillMaxHeight()
          .background(Color(0x30C4B4ED), RoundedCornerShape(8.dp))
          .border(4.dp, Color(0xFFC4B4ED), RoundedCornerShape(8.dp))
      )
    }
  }
}

@Composable
fun ThreeColumnsLayout(
  modifier: Modifier,
  animatedContentScope: AnimatedContentScope,
  sharedTransitionScope: SharedTransitionScope,
) {
  Row(modifier.padding(16.dp)) {
    with(sharedTransitionScope) {
      Box(
        Modifier
          .sharedElement(
            rememberSharedContentState("red"),
            animatedContentScope,
            zIndexInOverlay = 1f,
            boundsTransform = springyBoundsTransform,
          )
          .weight(.25f)
          .fillMaxHeight()
          .background(Color(0x30C4B4ED), RoundedCornerShape(8.dp))
          .border(4.dp, Color(0xFFC4B4ED), RoundedCornerShape(8.dp))
      )

      Spacer(Modifier.width(8.dp))

      Box(
        Modifier
          .sharedElement(
            rememberSharedContentState("blue"),
            animatedContentScope,
            zIndexInOverlay = 0f,
            boundsTransform = springyBoundsTransform,
          )
          .weight(.5f)
          .fillMaxHeight()
          .background(Color(0x30C4B4ED), RoundedCornerShape(8.dp))
          .border(4.dp, Color(0xFFC4B4ED), RoundedCornerShape(8.dp))
      )

      Spacer(Modifier.width(8.dp))

      Box(
        Modifier
          .sharedElement(
            rememberSharedContentState("green"),
            animatedContentScope,
            zIndexInOverlay = 2f,
            boundsTransform = springyBoundsTransform,
          )
          .weight(.25f)
          .fillMaxHeight()
          .background(Color(0x30C4B4ED), RoundedCornerShape(8.dp))
          .border(4.dp, Color(0xFFC4B4ED), RoundedCornerShape(8.dp))
      )
    }
  }
}

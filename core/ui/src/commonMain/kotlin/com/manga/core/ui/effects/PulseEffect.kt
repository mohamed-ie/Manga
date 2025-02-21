package com.manga.core.ui.effects

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun Modifier.pulseEffect(
    enabled: Boolean = true,
    initialRadius: Dp = 5.dp,
    targetRadius: Dp = 25.dp,
    targetColor: Color = MaterialTheme.colorScheme.primary.copy(0f),
    initialColor: Color = MaterialTheme.colorScheme.primary.copy(.6f),
    radiusAnimationSpec: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        animation = tween(
            durationMillis = 1000,
            delayMillis = 100,
            easing = LinearOutSlowInEasing
        ),
        repeatMode = RepeatMode.Restart
    ),
    colorAnimationSpec: InfiniteRepeatableSpec<Color> = infiniteRepeatable(
        animation = tween(
            durationMillis = 1000,
            delayMillis = 100,
            easing = LinearOutSlowInEasing
        ),
        repeatMode = RepeatMode.Restart
    ),

    ): Modifier = composed {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_transition")
    val density = LocalDensity.current
    val targetRadiusPx = with(density) { targetRadius.toPx() }
    val initialRadiusPx = with(density) { initialRadius.toPx() }

    val animatedRadiusPx by infiniteTransition.animateFloat(
        initialValue = initialRadiusPx,
        targetValue = targetRadiusPx,
        animationSpec = radiusAnimationSpec,
        label = "radius_animation"
    )

    val animatedColor by infiniteTransition.animateColor(
        initialValue = initialColor,
        targetValue = targetColor,
        animationSpec = colorAnimationSpec,
        label = "color_animation"
    )

    drawWithContent {
        if (!enabled) {
            drawContent()
            return@drawWithContent
        }
        drawCircle(color = animatedColor, radius = animatedRadiusPx)
    }
}

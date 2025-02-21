package com.manga.core.ui.effects

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp


@Composable
fun Modifier.shimmerEffect(
    visible: Boolean,
    colors: List<Color> = listOf(
        Color(0xFFB8B5B5),
        Color(0xFF8F8B8B),
        Color(0xFFB8B5B5),
    ),
    shape: Shape = RectangleShape,
    lines: Int,
    lastLineWidthPercentage: Float = .5f,
    lineSpacing: Dp = 4.dp
): Modifier =
    composed {
        if (visible) {
            var size by remember { mutableStateOf(IntSize.Zero) }
            val transition = rememberInfiniteTransition(label = "Infinite Transition")
            val startOffsetX by transition.animateFloat(
                initialValue = -2 * size.width.toFloat(),
                targetValue = 2 * size.width.toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(1000),
                    repeatMode = RepeatMode.Restart
                ),
                label = "offsetX"
            )

            val brush = Brush.linearGradient(
                colors = colors,
                start = Offset(startOffsetX, 0f),
                end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
            )

            drawWithContent {
                val lineHeight = (size.height - (lines - 1) * lineSpacing.toPx()) / lines
                val width = size.width.toFloat()

                val lineSize = Size(width, lineHeight)
                val outline = shape.createOutline(lineSize, layoutDirection, this)
                //lines - 1
                repeat(lines - 1) { i ->
                    val y = (lineHeight * i) + (lineSpacing.toPx() * i)
                    inset(top = y, left = 0f, right = 0f, bottom = 0f) {
                        drawOutline(outline = outline, brush = brush)
                    }
                }

                val lastLineSize = Size(width * lastLineWidthPercentage, lineHeight)
                val lastLineOutline = shape.createOutline(lastLineSize, layoutDirection, this)

                //last line
                val y = (lineHeight * (lines - 1)) + (lineSpacing.toPx() * (lines - 1))
                inset(top = y, left = 0f, right = 0f, bottom = 0f) {
                    drawOutline(outline = lastLineOutline, brush = brush)
                }

            }.onGloballyPositioned { size = it.size }
        } else {
            Modifier
        }
    }

@Composable
fun Modifier.shimmerEffect(
    visible: Boolean,
    colors: List<Color> = listOf(
        Color(0xFFB8B5B5),
        Color(0xFF8F8B8B),
        Color(0xFFB8B5B5),
    ),
    shape: Shape = RectangleShape
): Modifier =
    composed {
        if (visible) {
            var size by remember { mutableStateOf(IntSize.Zero) }
            val transition = rememberInfiniteTransition(label = "Infinite Transition")
            val startOffsetX by transition.animateFloat(
                initialValue = -2 * size.width.toFloat(),
                targetValue = 2 * size.width.toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(1000),
                    repeatMode = RepeatMode.Restart
                ),
                label = "offsetX"
            )

            background(
                brush = Brush.linearGradient(
                    colors = colors,
                    start = Offset(startOffsetX, 0f),
                    end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
                ),
                shape = shape
            )
                .drawWithContent { }
                .onGloballyPositioned { size = it.size }
        } else {
            Modifier
        }
    }

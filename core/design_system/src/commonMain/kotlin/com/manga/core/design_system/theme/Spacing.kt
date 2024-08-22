package com.manga.core.design_system.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val extraExtraSmall: Dp = 2.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 6.dp,
    val normal: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp,
    val extraExtraLarge: Dp = 64.dp,
)

@Stable
class PaddingValuesProvider {
    var start: Dp = 0.dp
    var top: Dp = 0.dp
    var bottom: Dp = 0.dp
    var end: Dp = 0.dp

    var horizontal: Dp = 0.dp
        set(value) {
            field = value
            start = value
            end = value
        }

    var vertical: Dp = 0.dp
        set(value) {
            field = value
            top = value
            bottom = value
        }
}

fun PaddingValuesProvider.toPaddingValues() = PaddingValues(
    top = top,
    bottom = bottom,
    start = start,
    end = end
)

@Composable
inline fun Modifier.spacing(block: PaddingValuesProvider.(Spacing) -> Unit) =
    padding(PaddingValuesProvider().apply { block(MaterialTheme.spacing) }.toPaddingValues())


val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing
    @Composable
    get() = LocalSpacing.current
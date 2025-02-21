package com.manga.core.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.manga.core.ui.effects.pulseEffect

@Composable
fun LoadingContent(modifier: Modifier = Modifier) =
    Box(modifier = modifier.pulseEffect())
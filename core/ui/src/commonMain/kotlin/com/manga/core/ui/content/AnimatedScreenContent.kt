package com.manga.core.ui.content

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun <S : Any> AnimatedScreenContent(
    targetState: S,
    modifier: Modifier = Modifier,
    contentKey: (targetState: S) -> Any? = { it },
    content: @Composable AnimatedContentScope.(S) -> Unit
) = AnimatedContent(
    modifier = modifier,
    targetState = targetState,
    transitionSpec = { fadeIn() togetherWith fadeOut() },
    contentKey = contentKey,
    content = content
)

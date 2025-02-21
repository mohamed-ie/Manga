package com.manga.core.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.manga.core.design_system.icons.MangaIcons
import com.manga.core.ui.content.ErrorContent
import com.manga.core.ui.content.LoadingContent
import kotlinx.coroutines.launch
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_content_description_scroll_to_top
import org.jetbrains.compose.resources.stringResource

object MangaScreenDefaults {
    @Composable
    fun ScrollToTopFloatingActionButton(onClick: suspend () -> Unit) {
        val scope = rememberCoroutineScope()

        FloatingActionButton(
            onClick = { scope.launch { onClick() } }
        ) {
            Icon(
                MangaIcons.Common.scrollToTop,
                contentDescription = stringResource(Res.string.core_ui_content_description_scroll_to_top)
            )
        }
    }

    @Composable
    fun LoadingState() =
        LoadingContent(Modifier.fillMaxSize())

    @Composable
    fun ErrorState(
        message: UiText,
        onRetry: () -> Unit
    ) = ErrorContent(
        message = message.asString(),
        modifier = Modifier.fillMaxSize(),
        onRetry = onRetry
    )
}
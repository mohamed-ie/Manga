package com.manga.core.ui.content

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.compose.utils.app_event.LocalAppEventInvoker
import com.manga.core.ui.LazyPagingItems
import com.manga.core.ui.LazyPagingItemsContentState
import com.manga.core.ui.UiText
import com.manga.core.ui.app_event.AppEvent
import com.manga.core.ui.contentState
import com.manga.core.ui.resource
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_action_retry
import manga.core.ui.generated.resources.core_ui_message_failed_to_load_content
import manga.core.ui.generated.resources.core_ui_message_unexpected_exception
import org.jetbrains.compose.resources.stringResource


@Composable
fun <T : Any> AnimatedLazyPagingItemsContent(
    items: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val appEventInvoker = LocalAppEventInvoker.current

    LaunchedEffect(items.loadState) {
        val error = items.contentState as? LazyPagingItemsContentState.Error ?: return@LaunchedEffect
        if (error is LazyPagingItemsContentState.Error.RefreshError) return@LaunchedEffect
        appEventInvoker?.send(
            AppEvent.ShowSnackbar(
                message = UiText.resource(Res.string.core_ui_message_failed_to_load_content),
                actionLabel = UiText.resource(Res.string.core_ui_action_retry),
                action = items::retry
            )
        )
    }

    AnimatedContent(
        modifier = modifier,
        targetState = items.contentState,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        contentKey = { it::class.simpleName },
        content = { targetContentState ->
            when (targetContentState) {
                is LazyPagingItemsContentState.Empty ->
                    EmptyContent(Modifier.fillMaxSize())

                is LazyPagingItemsContentState.Loading ->
                    LoadingContent(Modifier.fillMaxSize())

                is LazyPagingItemsContentState.Error.RefreshError ->
                    ErrorContent(
                        message = stringResource(Res.string.core_ui_message_unexpected_exception),
                        onRetry = items::retry
                    )

                is LazyPagingItemsContentState.NotLoading,
                is LazyPagingItemsContentState.Refreshing,
                is LazyPagingItemsContentState.Error.AppendError,
                is LazyPagingItemsContentState.Error.PrependError ->
                    content()
            }
        }
    )
}
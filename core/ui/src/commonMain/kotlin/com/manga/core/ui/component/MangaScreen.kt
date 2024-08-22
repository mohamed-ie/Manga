package com.manga.core.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.manga.core.ui.pulse
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_action_retry
import org.jetbrains.compose.resources.stringResource

@Composable
fun <S : Any> rememberMangaScreenState(
    targetState: S,
    refreshEnabled: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    showFloatingIconButton: Boolean = false,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) = remember(
    targetState,
    refreshEnabled,
    isRefreshing,
    onRefresh,
    showFloatingIconButton,
    snackbarHostState
) {
    MangaScreenState(
        targetState = targetState,
        refreshEnabled = refreshEnabled,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        showFloatingIconButton = showFloatingIconButton,
        snackbarHostState = snackbarHostState
    )
}

@Stable
class MangaScreenState<S : Any>(
    val targetState: S,
    val refreshEnabled: Boolean = false,
    val isRefreshing: Boolean = false,
    val onRefresh: (() -> Unit)? = null,
    val showFloatingIconButton: Boolean,
    val snackbarHostState: SnackbarHostState
) {
    suspend fun showSnackbar(message: String, actionLabel: String? = null) =
        snackbarHostState.showSnackbar(message, actionLabel) == SnackbarResult.ActionPerformed

    suspend fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        action: (() -> Unit)? = null
    ) {
        if (showSnackbar(message, actionLabel)) action?.invoke()
    }
}

@Composable
fun <S : Any> MangaScreen(
    modifier: Modifier = Modifier,
    screenState: MangaScreenState<S>,
    floatingActionButton: @Composable AnimatedVisibilityScope.() -> Unit = {},
    topBar: @Composable () -> Unit = {},
    content: @Composable AnimatedContentScope.(S) -> Unit
) = Scaffold(
    modifier = modifier,
    contentWindowInsets = WindowInsets(0),
    snackbarHost = { SnackbarHost(hostState = screenState.snackbarHostState) },
    topBar = topBar,
    floatingActionButton = {
        AnimatedVisibility(
            visible = screenState.showFloatingIconButton,
            enter = scaleIn(),
            exit = scaleOut(),
            content = floatingActionButton
        )
    }
) {
    val pullToRefreshState = rememberPullToRefreshState()

    Box(
        Modifier
            .pullToRefresh(
                onRefresh = { screenState.onRefresh?.invoke() },
                isRefreshing = screenState.isRefreshing,
                state = pullToRefreshState,
                enabled = screenState.refreshEnabled
            )
            .fillMaxSize()
            .padding(it),
    ) {
        AnimatedContent(
            modifier = Modifier.fillMaxSize(),
            targetState = screenState.targetState,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            content = content
        )

        PullToRefreshDefaults.Indicator(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState,
            isRefreshing = screenState.isRefreshing
        )
    }
}

@Composable
fun AnimatedContentScope.LoadingContent(modifier: Modifier = Modifier) = Box(modifier = modifier.pulse())

@Composable
fun AnimatedContentScope.ErrorContent(message: String, modifier: Modifier = Modifier, onRetry: () -> Unit) =
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message)
        Spacer(Modifier.height(16.dp))
        TextButton(onClick = onRetry) {
            Text(text = stringResource(Res.string.core_ui_action_retry))
        }
    }
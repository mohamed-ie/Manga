package com.manga.core.ui.component

import androidx.compose.animation.*
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

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

    suspend fun showSnackbar(message: String, actionLabel: String? = null, action: (() -> Unit)? = null) {
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
) =
    Scaffold(
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
        MangaPullToRefreshBox(
            modifier = Modifier.fillMaxSize().padding(it),
            isRefreshing = screenState.isRefreshing,
            refreshEnabled = screenState.refreshEnabled,
            onRefresh = { screenState.onRefresh?.invoke() },
            content = {
                AnimatedContent(
                    modifier = Modifier.fillMaxSize(),
                    targetState = screenState.targetState,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    content = content
                )
            }
        )
    }

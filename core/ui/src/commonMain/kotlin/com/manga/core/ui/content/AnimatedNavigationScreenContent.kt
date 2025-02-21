package com.manga.core.ui.content

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <S : Any> AnimatedNavigationScreenContent(
    targetState: S,
    modifier: Modifier = Modifier,
    shouldShouldFloatingActionButton: Boolean = true,
    contentKey: (targetState: S) -> Any? = { it },
    floatingActionButton: @Composable AnimatedVisibilityScope.() -> Unit = {},
    content: @Composable AnimatedContentScope.(S) -> Unit
) = Box(modifier) {
    AnimatedScreenContent(
        modifier = Modifier.fillMaxSize(),
        targetState = targetState,
        contentKey = contentKey,
        content = content
    )

    AnimatedVisibility(
        modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
        visible = shouldShouldFloatingActionButton,
        enter = scaleIn(),
        exit = scaleOut(),
        content = floatingActionButton
    )
}

@Composable
fun NavigationScreenContent(
    modifier: Modifier = Modifier,
    shouldShouldFloatingActionButton: Boolean = true,
    floatingActionButton: @Composable AnimatedVisibilityScope.() -> Unit = {},
    content: @Composable () -> Unit
) = Box(modifier) {
    content()

    AnimatedVisibility(
        modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
        visible = shouldShouldFloatingActionButton,
        enter = scaleIn(),
        exit = scaleOut(),
        content = floatingActionButton
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <S : Any> AnimatedNavigationScreenContent(
    targetState: S,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    refreshEnabled: Boolean = true,
    shouldShouldFloatingActionButton: Boolean = true,
    contentKey: (targetState: S) -> Any? = { it },
    floatingActionButton: @Composable AnimatedVisibilityScope.() -> Unit = {},
    content: @Composable AnimatedContentScope.(S) -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    Box(
        modifier = modifier
            .pullToRefresh(
                onRefresh = onRefresh,
                isRefreshing = isRefreshing,
                state = pullToRefreshState,
                enabled = refreshEnabled
            ),
    ) {

        AnimatedScreenContent(
            modifier = Modifier.fillMaxSize(),
            targetState = targetState,
            contentKey = contentKey,
            content = content
        )

        AnimatedVisibility(
            modifier = Modifier.padding(16.dp).align(Alignment.BottomEnd),
            visible = shouldShouldFloatingActionButton,
            enter = scaleIn(),
            exit = scaleOut(),
            content = floatingActionButton
        )
    }
}
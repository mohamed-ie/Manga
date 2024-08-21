package com.manga.core.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Dp


@Composable
fun MangaPullToRefreshBox(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    propagateMinConstraints: Boolean = false,
    positionalThreshold: Dp = PullToRefreshDefaults.PositionalThreshold,
    refreshEnabled: Boolean = true,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    val state = rememberPullToRefreshState(enabled = { refreshEnabled }, positionalThreshold = positionalThreshold)

    LaunchedEffect(key1 = state.isRefreshing) {
        if (state.isRefreshing) {
            onRefresh()
            return@LaunchedEffect
        }
    }

    LaunchedEffect(key1 = isRefreshing) {
        if (isRefreshing) return@LaunchedEffect
        state.endRefresh()
    }

    Box(
        modifier = modifier.nestedScroll(state.nestedScrollConnection),
        contentAlignment = contentAlignment,
        propagateMinConstraints = propagateMinConstraints,
    ) {
        content()
        PullToRefreshContainer(
            state = state,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

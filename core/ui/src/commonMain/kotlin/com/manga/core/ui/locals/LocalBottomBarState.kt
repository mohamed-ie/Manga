package com.manga.core.ui.locals

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Stable
class BottomBarState {
    var visible by mutableStateOf(true)
        private set

    fun hide() {
        visible = false
    }

    fun show() {
        visible = true
    }
}

val LocalBottomBarState = compositionLocalOf<BottomBarState?> { null }

@OptIn(FlowPreview::class)
@Composable
fun BottomBarState.updateVisibilityFrom(
    scrollableState: ScrollableState
) {
    LaunchedEffect(scrollableState) {
        snapshotFlow { scrollableState.lastScrolledForward }
            .debounce(500)
            .distinctUntilChanged()
            .onEach { if (it) hide() else show() }
            .launchIn(this)
    }
}
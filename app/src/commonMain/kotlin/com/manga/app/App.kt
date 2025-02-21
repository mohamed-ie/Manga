package com.manga.app

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.utils.app_event.LocalAppEventInvoker
import com.manga.app.ui.MangaApp
import com.manga.app.ui.MangaAppState
import com.manga.app.ui.rememberMangaAppState
import com.manga.core.design_system.theme.MangaTheme
import com.manga.core.ui.locals.LocalBottomBarState
import com.manga.core.ui.locals.LocalTimeZone
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    val appState: MangaAppState = rememberMangaAppState(
        appEventInvoker = koinInject(),
        networkMonitor = koinInject(),
        timeZoneMonitor = koinInject()
    )

    val currentTimeZone by appState.currentTimeZone.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    MangaTheme {
        CompositionLocalProvider(
            LocalTimeZone provides currentTimeZone,
            LocalAppEventInvoker provides appState.appEventInvoker,
            LocalBottomBarState provides appState.bottomBarState
        ) {
            MangaApp(appState, snackbarHostState)
        }
    }
}
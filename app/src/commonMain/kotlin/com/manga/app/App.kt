package com.manga.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manga.app.ui.MangaApp
import com.manga.app.ui.MangaAppState
import com.manga.app.ui.rememberMangaAppState
import com.manga.core.design_system.theme.MangaTheme
import com.manga.core.ui.LocalTimeZone
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    val appState: MangaAppState = rememberMangaAppState(networkMonitor = koinInject(), timeZoneMonitor = koinInject())

    val currentTimeZone by appState.currentTimeZone.collectAsStateWithLifecycle()
    
    MangaTheme {
        CompositionLocalProvider(LocalTimeZone provides currentTimeZone) {
            MangaApp(appState)
        }
    }
}
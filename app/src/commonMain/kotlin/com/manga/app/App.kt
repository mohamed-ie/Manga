package com.manga.app

import androidx.compose.runtime.Composable
import com.manga.app.app.MangaApp
import com.manga.app.app.rememberMangaAppState
import com.manga.core.design_system.theme.MangaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    MangaTheme {
        MangaApp(rememberMangaAppState(koinInject()))
    }
}
package com.manga.app.ui

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.manga.app.navigation.MangaNavHost

@Composable
fun MangaApp(
    appState: MangaAppState,
    snackbarHostState: SnackbarHostState
) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomBar(appState) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {innerPadding->
        MangaNavHost(
            modifier = Modifier.padding(innerPadding).consumeWindowInsets(innerPadding),
            appState = appState
        )
    }
}
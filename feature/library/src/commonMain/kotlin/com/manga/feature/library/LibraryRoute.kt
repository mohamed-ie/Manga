package com.manga.feature.library

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun LibraryRoute(
    viewModel: LibraryViewModel = koinViewModel()
) {

    LibraryScreen(
        onEvent = { event ->
        }
    )
}
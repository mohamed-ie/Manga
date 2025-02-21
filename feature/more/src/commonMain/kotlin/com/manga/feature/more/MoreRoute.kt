package com.manga.feature.more

import androidx.compose.runtime.Composable
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MoreRoute(
    viewModel: MoreViewModel = koinViewModel()
) {

    MoreScreen(
        onEvent = { event ->
        }
    )
}
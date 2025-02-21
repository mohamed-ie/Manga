package com.manga.feature.explore

import androidx.compose.runtime.Composable
import com.manga.core.ui.collectAsLazyPagingItems
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ExploreRoute(
    navigateToManga: (String) -> Unit,
    navigateToChapter: (String) -> Unit,
    viewModel: ExploreViewModel = koinViewModel()
) {
    val mangaList = viewModel.manga.collectAsLazyPagingItems()

    ExploreScreen(
        mangaList = mangaList,
        onEvent = { event ->
            when (event) {
                is ExploreEvent.OpenChapter -> navigateToChapter(event.manga.id)
                is ExploreEvent.OpenManga -> navigateToManga(event.minManga.id)
            }
        }
    )
}
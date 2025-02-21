package com.manga.feature.explore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.manga.core.model.manga.Manga
import com.manga.core.ui.LazyPagingItems
import com.manga.core.ui.MangaScreenDefaults
import com.manga.core.ui.content.AnimatedLazyPagingItemsContent
import com.manga.core.ui.content.NavigationScreenContent
import com.manga.core.ui.locals.LocalBottomBarState
import com.manga.core.ui.locals.updateVisibilityFrom
import com.manga.feature.explore.state.SuccessState

@Composable
internal fun ExploreScreen(
    mangaList: LazyPagingItems<Manga>,
    onEvent: (ExploreEvent) -> Unit
) {
    val lazyGridState = rememberLazyGridState()
    val bottomBarState = LocalBottomBarState.current
    bottomBarState?.updateVisibilityFrom(lazyGridState)

    val firstItemVisible by remember { derivedStateOf { lazyGridState.firstVisibleItemIndex > 0 } }

    NavigationScreenContent(
        floatingActionButton = {
            MangaScreenDefaults.ScrollToTopFloatingActionButton { lazyGridState.animateScrollToItem(0) }
        },
        shouldShouldFloatingActionButton = firstItemVisible
    ) {
        AnimatedLazyPagingItemsContent(
            items = mangaList,
            content = {
                SuccessState(
                    modifier = Modifier.fillMaxSize(),
                    lazyGridState = lazyGridState,
                    mangaList = mangaList,
                    onMangaClick = { onEvent(ExploreEvent.OpenManga(it)) },
                    onChapterClick = { onEvent(ExploreEvent.OpenChapter(it)) }
                )
            }
        )
    }
}
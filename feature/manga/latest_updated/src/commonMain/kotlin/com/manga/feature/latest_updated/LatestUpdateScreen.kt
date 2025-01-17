package com.manga.feature.latest_updated

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.manga.core.model.manga_dex.chapter.MinChapter
import com.manga.core.model.manga_dex.manga.MinManga
import com.manga.core.ui.LazyPagingItems
import com.manga.core.ui.card.MangaCard
import com.manga.core.ui.collectAsLazyPagingItems
import com.manga.core.ui.component.ErrorContent
import com.manga.core.ui.component.LoadingContent
import com.manga.core.ui.component.MangaScreen
import com.manga.core.ui.component.MangaScreenDefaults
import com.manga.core.ui.component.MangaScreenState
import com.manga.core.ui.component.rememberMangaScreenState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_message_unexpected_exception
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun LatestUpdatedRoute(
    navigateToManga: (String) -> Unit,
    navigateToChapter: (String) -> Unit,
    viewModel: LatestUpdatedViewModel = koinViewModel()
) {
    val mangaList = viewModel.manga.collectAsLazyPagingItems()

    val screenState = rememberMangaScreenState(
        targetState = mangaList,
        refreshEnabled = mangaList.loadState.refresh is LoadState.NotLoading,
        onRefresh = mangaList::refresh,
        isRefreshing = mangaList.loadState.refresh is LoadState.Loading && mangaList.itemCount > 0
    )

    LatestUpdatedScreen(
        screenState = screenState,
        onEvent = { event ->
            when (event) {
                is LatestUpdatedEvent.OpenChapter -> navigateToChapter(event.minChapter.id)
                is LatestUpdatedEvent.OpenManga -> navigateToManga(event.minManga.id)
            }
        }
    )
}

@Composable
internal fun LatestUpdatedScreen(
    screenState: MangaScreenState<LazyPagingItems<MinManga>>,
    onEvent: (LatestUpdatedEvent) -> Unit
) {
    val lazyGridState = rememberLazyGridState()

    LaunchedEffect(lazyGridState) {
        snapshotFlow { lazyGridState.firstVisibleItemIndex }
            .mapLatest { it > 0 }
            .distinctUntilChanged()
            .collectLatest {
                screenState.showFloatingActionButton = it
            }
    }

    MangaScreen(
        screenState = screenState,
        floatingActionButton = {
            MangaScreenDefaults.ScrollToTopFAB { lazyGridState.animateScrollToItem(0) }
        },
        contentKey = {
            when {
                it.loadState.refresh is LoadState.Loading && it.itemCount == 0 -> 0
                it.loadState.refresh is LoadState.Error -> 1
                else -> 2
            }
        }
    ) { mangaList ->
        when {
            mangaList.loadState.refresh is LoadState.Loading && mangaList.itemCount == 0 -> LoadingContent()

            mangaList.loadState.refresh is LoadState.Error ->
                ErrorContent(
                    message = stringResource(Res.string.core_ui_message_unexpected_exception),
                    onRetry = mangaList::retry
                )

            else ->
                LatestUpdatedScreen(
                    modifier = Modifier.fillMaxSize(),
                    lazyGridState = lazyGridState,
                    mangaList = mangaList,
                    onMangaClick = { onEvent(LatestUpdatedEvent.OpenManga(it)) },
                    onChapterClick = { onEvent(LatestUpdatedEvent.OpenChapter(it)) }
                )
        }
    }
}

@Composable
internal fun LatestUpdatedScreen(
    modifier: Modifier = Modifier,
    lazyGridState: LazyGridState,
    mangaList: LazyPagingItems<MinManga>,
    onMangaClick: (MinManga) -> Unit,
    onChapterClick: (MinChapter) -> Unit
) {
    LazyVerticalGrid(
        state = lazyGridState,
        modifier = modifier,
        columns = GridCells.Fixed(3)
    ) {
        items(
            count = mangaList.itemCount,
            key = { mangaList.peek(it)?.run { lastChapter?.id ?: id } ?: 0 }) { index ->
            val manga = mangaList[index]
            MangaCard(
                modifier = Modifier.fillMaxWidth().aspectRatio(.5f)
                    .padding(horizontal = 4.dp, vertical = 8.dp),
                manga = manga,
                showChapter = true,
                showPublicationDemographic = false,
                showStatus = false,
                onClick = { manga?.let { onMangaClick(manga) } },
                onChapterClick = { manga?.lastChapter?.let { onChapterClick(it) } }
            )
        }

        if (mangaList.loadState.append == LoadState.Loading)
            items(mangaList.itemCount.mod(3).plus(4)) {
                MangaCard(
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(.5f)
                        .padding(horizontal = 4.dp, vertical = 8.dp),
                    manga = null,
                    showChapter = true,
                    showPublicationDemographic = false,
                    showStatus = false,
                    onClick = {},
                    onChapterClick = {}
                )
            }
    }
}
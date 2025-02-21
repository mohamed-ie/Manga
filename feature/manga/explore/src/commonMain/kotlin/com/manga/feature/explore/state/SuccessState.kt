package com.manga.feature.explore.state

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.manga.core.model.manga.Manga
import com.manga.core.ui.LazyPagingItems
import com.manga.core.ui.card.MangaCard


@Composable
internal fun SuccessState(
    modifier: Modifier = Modifier,
    lazyGridState: LazyGridState,
    mangaList: LazyPagingItems<Manga>,
    onMangaClick: (Manga) -> Unit,
    onChapterClick: (Manga) -> Unit
) {

    LazyVerticalGrid(
        state = lazyGridState,
        modifier = modifier,
        columns = GridCells.Fixed(3)
    ) {
        items(
            count = mangaList.itemCount,
            key = { mangaList.peek(it)?.run { lastChapter?.id ?: id } ?: it }
        ) { index ->
            val manga = mangaList[index]
            MangaCard(
                modifier = Modifier.fillMaxWidth().aspectRatio(.5f)
                    .padding(horizontal = 4.dp, vertical = 8.dp),
                manga = manga,
                showPublicationDemographic = false,
                showStatus = false,
                onClick = { manga?.let { onMangaClick(manga) } },
                onChapterClick = { manga?.let { onChapterClick(it) } }
            )
        }

        if (mangaList.loadState.append == LoadState.Loading)
            items(mangaList.itemCount.mod(3).plus(4)) {
                MangaCard(
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(.5f)
                        .padding(horizontal = 4.dp, vertical = 8.dp),
                    manga = null,
                    showPublicationDemographic = false,
                    showStatus = false,
                    onClick = {},
                    onChapterClick = {}
                )
            }
    }
}
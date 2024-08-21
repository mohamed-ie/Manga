package com.manga.feature.home

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import com.manga.core.design_system.theme.MangaTheme
import com.manga.core.model.manga.MinManga
import com.manga.core.ui.card.MangaCard
import core.ui.com.manga.core.ui.LazyPagingItems
import core.ui.com.manga.core.ui.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flowOf
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_text_latest_updates
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeRoute(viewModel: HomeViewModel = koinViewModel()) {
    val manga = viewModel.manga.collectAsLazyPagingItems()
    HomeScreen(modifier = Modifier, latestUpdates = manga)
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    latestUpdates: LazyPagingItems<MinManga>
) {
    LazyColumn(modifier=modifier) {
        item {
            Text(
                text = stringResource(Res.string.core_ui_text_latest_updates),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
        }
        item {
            MangaLazyRowUpdates(modifier=Modifier.fillParentMaxHeight(.5f),manga = latestUpdates)
        }
    }
}

@Composable
internal fun MangaLazyRowUpdates(
    modifier: Modifier = Modifier,
    manga: LazyPagingItems<MinManga>
) = LazyRow(modifier = modifier) {
    items(count = manga.itemCount, key = { index -> manga[index]?.id ?: index }) { index ->
        val mangaItem = manga[index]
        MangaCard(
            modifier = Modifier.fillMaxHeight().padding(8.dp),
            manga = mangaItem,
            onClick = {},
            onChapterClick = {}
        )
    }
}

@Preview
@Composable
private fun Preview() {
    val manga = flowOf(PagingData.empty<MinManga>()).collectAsLazyPagingItems()
    MangaTheme {
        HomeScreen(latestUpdates = manga)
    }
}
package com.manga.feature.home

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import com.manga.core.model.manga.MinManga
import com.manga.core.model.manga.NewReleaseManga
import com.manga.core.ui.card.MangaCard
import com.manga.core.ui.component.MangaScreen
import com.manga.core.ui.component.MangaScreenState
import com.manga.core.ui.component.rememberMangaScreenState
import core.ui.com.manga.core.ui.LazyPagingItems
import core.ui.com.manga.core.ui.MangaSubComposeAsyncImage
import core.ui.com.manga.core.ui.collectAsLazyPagingItems
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_text_latest_updates
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun HomeRoute(viewModel: HomeViewModel = koinViewModel()) {
    val latestUpdates = viewModel.latestUpdatesManga.collectAsLazyPagingItems()
    val hightestRatingManga = viewModel.hightestRatingManga.collectAsLazyPagingItems()
    val recentlyAddedManga = viewModel.recentlyAddedManga.collectAsLazyPagingItems()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val screenState = rememberMangaScreenState(targetState = uiState)
    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.initialize()
    }

    HomeScreen(
        modifier = Modifier,
        latestUpdates = latestUpdates,
        hightestRatingManga = hightestRatingManga,
        recentlyAddedManga = recentlyAddedManga
    )
}

@Composable
internal fun HomeScreen(
    latestUpdates: LazyPagingItems<MinManga>,
    hightestRatingManga: LazyPagingItems<MinManga>,
    recentlyAddedManga: LazyPagingItems<MinManga>,
    screenState: MangaScreenState<HomeUiState>
) = MangaScreen(
    modifier = Modifier.fillMaxSize(),
    screenState = screenState
) { uiState ->
    when (uiState) {
        HomeUiState.Loading -> {}
        is HomeUiState.Failure -> {}
        is HomeUiState.Success ->
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                latestUpdates = latestUpdates,
                hightestRatingManga = hightestRatingManga,
                recentlyAddedManga = recentlyAddedManga,
                uiState = uiState
            )
    }
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    latestUpdates: LazyPagingItems<MinManga>,
    hightestRatingManga: LazyPagingItems<MinManga>,
    recentlyAddedManga: LazyPagingItems<MinManga>,
    uiState: HomeUiState.Success
) {
    LazyColumn(modifier = modifier) {
        item {
            NewReleaseCard(
                modifier = Modifier.fillMaxWidth().fillParentMaxHeight(.5f),
                manga =
            )
        }
        item {
            Text(
                text = stringResource(Res.string.core_ui_text_latest_updates),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
        }
        item {
            MangaLazyRowUpdates(modifier = Modifier.fillParentMaxHeight(.5f), manga = latestUpdates)
        }

        item {
            Text(
                text = stringResource(Res.string.core_ui_text_latest_updates),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
        }
        item {
            MangaLazyRowUpdates(modifier = Modifier.fillParentMaxHeight(.5f), manga = hightestRatingManga)
        }
        item {
            Text(
                text = stringResource(Res.string.core_ui_text_latest_updates),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Medium
            )
        }
        item {
            MangaLazyRowUpdates(modifier = Modifier.fillParentMaxHeight(.5f), manga = recentlyAddedManga)
        }
    }
}

@Composable
internal fun MangaLazyRowUpdates(
    modifier: Modifier = Modifier,
    manga: LazyPagingItems<MinManga>
) = LazyRow(modifier = modifier) {

    if (manga.itemCount == 0 && manga.loadState.refresh == LoadState.Loading)
        items(count = 5) {
            MangaCard(
                modifier = Modifier.fillMaxHeight().aspectRatio(.5f).padding(8.dp),
                manga = null,
                onClick = {},
                onChapterClick = {}
            )
        }

    items(count = manga.itemCount, key = { index -> manga[index]?.id ?: index }) { index ->
        val mangaItem = manga[index]
        MangaCard(
            modifier = Modifier.fillMaxHeight().aspectRatio(.5f).padding(8.dp),
            manga = mangaItem,
            onClick = {},
            onChapterClick = {}
        )
    }

    if (manga.loadState.append is LoadState.Loading)
        items(count = 2, key = { -it }) {
            MangaCard(
                modifier = Modifier.fillMaxHeight().aspectRatio(.5f).padding(8.dp),
                manga = null,
                onClick = {},
                onChapterClick = {}
            )
        }
}

//@Composable
//fun NewReleaseHorizontalPager(modifier: Modifier = Modifier) =
//    HorizontalPager(
//
//    )


@Composable
fun NewReleaseCard(
    modifier: Modifier = Modifier,
    manga: NewReleaseManga
) {
    ElevatedCard(modifier = modifier) {
        MangaSubComposeAsyncImage(
            modifier = Modifier.fillMaxSize().graphicsLayer {
                renderEffect = BlurEffect(25f, 25f, TileMode.Mirror)
            },
            model = manga.cover,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Row(modifier = Modifier.fillMaxSize()) {
            Column {
                Text(
                    text = manga.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row() {

                }
                Text(text = manga.description)
            }
        }
    }
}

@Composable
fun Tag(modifier: Modifier = Modifier, text: String) = Text(
    modifier = modifier
        .padding(vertical = 4.dp, horizontal = 8.dp)
        .clip(MaterialTheme.shapes.small)
        .background(MaterialTheme.colorScheme.surfaceVariant),
    text = text,
    style = MaterialTheme.typography.labelSmall,
    color = MaterialTheme.colorScheme.onSurfaceVariant
)
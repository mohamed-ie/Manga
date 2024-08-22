package com.manga.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manga.core.design_system.icons.MangaIcons
import com.manga.core.design_system.theme.spacing
import com.manga.core.model.chapter.MinChapter
import com.manga.core.model.manga.MinManga
import com.manga.core.ui.FeaturedMangaHorizontalPager
import com.manga.core.ui.card.MangaCard
import com.manga.core.ui.component.ErrorContent
import com.manga.core.ui.component.LoadingContent
import com.manga.core.ui.component.MangaScreen
import com.manga.core.ui.component.MangaScreenState
import com.manga.core.ui.component.rememberMangaScreenState
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_content_description_view_more
import manga.core.ui.generated.resources.core_ui_text_latest_updates
import manga.core.ui.generated.resources.core_ui_text_new_manga
import manga.core.ui.generated.resources.core_ui_text_popular_this_year
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

private val titlesRes = arrayOf(
    Res.string.core_ui_text_latest_updates to true,
    Res.string.core_ui_text_new_manga to false,
    Res.string.core_ui_text_popular_this_year to false,
)

@Composable
internal fun HomeRoute(viewModel: HomeViewModel = koinViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val screenState = rememberMangaScreenState(targetState = uiState)

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.initialize()
    }

    HomeScreen(screenState = screenState)
}

@Composable
internal fun HomeScreen(screenState: MangaScreenState<HomeUiState>) = MangaScreen(
    modifier = Modifier.fillMaxSize(),
    screenState = screenState
) { uiState ->
    when (uiState) {
        HomeUiState.Loading -> LoadingContent()
        is HomeUiState.Failure -> ErrorContent(uiState.message, onRetry = {})
        is HomeUiState.Success ->
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                uiState = uiState
            )
    }
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState.Success
) = LazyColumn(modifier = modifier) {
    item {
        FeaturedMangaHorizontalPager(
            modifier = Modifier.fillParentMaxHeight(.4f).fillParentMaxWidth(),
            mangaList = uiState.newPopuler,
            onMangaClick = {}
        )
    }

    titlesRes.forEachIndexed { index, (res, showChapter) ->
        mangaList(
            titleRes = res,
            mangaList = when (index) {
                0 -> uiState.latestUpdates
                1 -> uiState.newRelease
                2 -> uiState.popularThisYear
                else -> emptyList()
            },
            showChapter = showChapter,
            onViewMoreClick = {},
            onMangaClick = {},
            onChapterClick = {}
        )
    }
}


internal fun LazyListScope.mangaList(
    titleRes: StringResource,
    mangaList: List<MinManga>,
    showChapter: Boolean,
    onViewMoreClick: () -> Unit,
    onMangaClick: (MinManga) -> Unit,
    onChapterClick: (MinChapter) -> Unit
) {
    item {
        MangaListTitleRow(
            text = stringResource(titleRes),
            onViewMoreClick = onViewMoreClick
        )
    }

    item {
        MangaLazyRow(
            modifier = Modifier.fillParentMaxHeight(.5f),
            mangaList = mangaList,
            showChapter = showChapter,
            onMangaClick = onMangaClick,
            onChapterClick = onChapterClick
        )
    }
}

@Composable
internal fun MangaListTitleRow(
    text: String,
    onViewMoreClick: () -> Unit
) = Row(
    modifier = Modifier.fillMaxSize().spacing {
        start = it.medium
        top = it.medium
    },
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Medium
    )

    IconButton(onClick = onViewMoreClick) {
        Icon(
            imageVector = MangaIcons.Common.viewMore,
            contentDescription = stringResource(Res.string.core_ui_content_description_view_more)
        )
    }
}

@Composable
internal fun MangaLazyRow(
    modifier: Modifier = Modifier,
    mangaList: List<MinManga>,
    showChapter: Boolean,
    onMangaClick: (MinManga) -> Unit,
    onChapterClick: (MinChapter) -> Unit
) = LazyRow(modifier = modifier) {
    items(items = mangaList, key = MinManga::id) { manga ->
        MangaCard(
            modifier = Modifier.fillMaxHeight().aspectRatio(.5f).padding(8.dp),
            manga = manga,
            onClick = { onMangaClick(manga) },
            onChapterClick = { manga.lastChapter?.let { onChapterClick(it) } },
            showChapter = showChapter
        )
    }
}
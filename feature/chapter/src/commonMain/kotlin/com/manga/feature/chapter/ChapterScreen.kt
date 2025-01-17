package com.manga.feature.chapter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.manga.core.ui.component.ErrorContent
import com.manga.core.ui.component.LoadingContent
import com.manga.core.ui.component.MangaScreen
import com.manga.core.ui.component.MangaScreenState
import com.manga.core.ui.component.MangaSubComposeAsyncImage
import com.manga.core.ui.component.rememberMangaScreenState
import kotlinx.coroutines.flow.onEach
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ChapterRoute(
    viewModel: ChapterViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val screenState = rememberMangaScreenState(
        targetState = uiState
    )

    LaunchedEffect(viewModel) {
        viewModel.uiEvent.onEach { uiEvent ->
            when (uiEvent) {
                is ChapterUiEvent.ShowSnackbar -> screenState.showSnackbar(uiEvent)
            }
        }
    }

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.initialize()
    }

    ChapterScreen(
        screenState = screenState,
        onEvent = {}
    )
}

@Composable
internal fun ChapterScreen(
    screenState: MangaScreenState<ChapterUiState>,
    onEvent: (ChapterEvent) -> Unit
) = MangaScreen(
    modifier = Modifier.fillMaxSize(),
    screenState = screenState,
) { uiState ->
    when (uiState) {
        ChapterUiState.Loading -> LoadingContent()

        is ChapterUiState.Failure -> ErrorContent(
            uiState.message,
            onRetry = { onEvent(ChapterEvent.Retry) })

        is ChapterUiState.Success ->
            ChapterScreen(
                modifier = Modifier.fillMaxSize(),
                uiState = uiState,
                onEvent = onEvent
            )
    }
}

@Composable
internal fun ChapterScreen(
    modifier: Modifier = Modifier,
    uiState: ChapterUiState.Success,
    onEvent: (ChapterEvent) -> Unit,
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        uiState.chapter
            .images
            .forEach {
                key(it) {
                    PageItem(
                        modifier = Modifier.fillMaxWidth(),
                        model = it
                    )
                }
            }
    }
}

@Composable
internal fun PageItem(
    modifier: Modifier = Modifier,
    model: String,
) = MangaSubComposeAsyncImage(
    model = model,
    modifier = modifier,
    contentScale = ContentScale.FillWidth,
    loading = {
        Box(
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    },
    contentDescription = null
)
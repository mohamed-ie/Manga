package com.manga.feature.home

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manga.core.common.getOrThrow
import com.manga.core.data.repository.manga_central.MangaCentralRepository
import com.manga.core.model.chapter.MinChapter
import com.manga.core.model.chapter.request.ChapterListRequest
import com.manga.core.model.common.MangaDexSortedOrder
import com.manga.core.model.common.descOrder
import com.manga.core.model.manga.MinManga
import com.manga.core.model.manga.request.MangaListRequest
import com.manga.core.ui.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.*
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class HomeViewModel(
    private val mangaCentralRepository: MangaCentralRepository
) : ViewModel() {
    private val _uiEvent = MutableSharedFlow<HomeUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var initializeCalled = false

    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true
        initialLoad()
    }

    private fun initialLoad() {
        loadData(
            onFailure = { _uiState.value = HomeUiState.Failure(it) },
            onSuccess = { _uiState.value = it }
        )
    }

    private fun loadData(onFailure: (UiText) -> Unit, onSuccess: (HomeUiState.Success) -> Unit) =
        viewModelScope.launch(onFailure) {
            val newPopular = mangaCentralRepository.minMangaList(
                request = MangaListRequest(
                    order = listOf(MangaDexSortedOrder(MangaListRequest.Order.FollowedCount)),
                    createdAtSince = Clock.System.now().minus(1, DateTimeUnit.MONTH, TimeZone.UTC),
                    limit = 10
                ),
                withStatistics = true
            ).getOrThrow()

            val newManga = mangaCentralRepository.minMangaList(
                request = MangaListRequest(
                    order = listOf(MangaDexSortedOrder(MangaListRequest.Order.CreatedAt)),
                    limit = 24,
                    hasAvailableChapters = true
                )
            ).getOrThrow()

            val popularThisYear = mangaCentralRepository.minMangaList(
                request = MangaListRequest(
                    order = listOf(MangaDexSortedOrder(MangaListRequest.Order.FollowedCount)),
                    year = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year,
                    limit = 24,
                    hasAvailableChapters = true
                )
            ).getOrThrow()

            val latestUpdates = mangaCentralRepository.minMangaList(
                request = ChapterListRequest(
                    limit = 24,
                    order = listOf(ChapterListRequest.Order.ReadableAt.descOrder)
                ),
                withStatistics = true
            ).getOrThrow()

            _uiState.value = HomeUiState.Success(
                newManga = newManga,
                newPopuler = newPopular,
                popularThisYear = popularThisYear,
                latestUpdated = latestUpdates
            )
        }


    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.Refresh -> refresh()
            HomeEvent.Retry -> initialLoad()
            else -> Unit
        }
    }

    private fun refresh() {
        _uiState.updateSuccess { it.copy(refreshing = true) }

        loadData(
            onFailure = { _uiEvent.launchEmit(HomeUiEvent.ShowSnackbar(it)) },
            onSuccess = { _uiState.value = it }
        ).invokeOnCompletion {
            _uiState.updateSuccess { it.copy(refreshing = false) }
        }
    }

}

internal sealed interface HomeUiState : SuccessUiState<HomeUiState.Success> {
    data class Success(
        val refreshing: Boolean = false,
        val newManga: List<MinManga>,
        val newPopuler: List<MinManga>,
        val popularThisYear: List<MinManga>,
        val latestUpdated: List<MinManga>
    ) : HomeUiState

    data object Loading : HomeUiState

    data class Failure(val message: UiText) : HomeUiState
}

internal sealed interface HomeEvent {
    data object Retry : HomeEvent
    data object Refresh : HomeEvent
    data class OpenChapter(val chapter: MinChapter) : HomeEvent
    data class OpenManga(val manga: MinManga) : HomeEvent
    data class OpenMangaList(val listIndex: Int) : HomeEvent
}

internal sealed interface HomeUiEvent {
    data class ShowSnackbar(
        override val message: UiText,
        override val actionLabel: UiText? = null,
        override val action: (() -> Unit)? = null
    ) : HomeUiEvent, ScreenUiEvent.ShowSnackbarAction
}


package com.manga.feature.home

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manga.core.common.getOrThrow
import com.manga.core.data.repository.manga.MangaRepository
import com.manga.core.data.repository.manga_central.MangaCentralRepository
import com.manga.core.model.chapter.request.ChapterListRequest
import com.manga.core.model.common.MangaDexSortedOrder
import com.manga.core.model.common.descOrder
import com.manga.core.model.manga.MinManga
import com.manga.core.model.manga.request.MangaListRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class HomeViewModel(
    private val mangaCentralRepository: MangaCentralRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var initializeCalled = false

    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val newPopular = mangaCentralRepository.minMangaList(
                request = MangaListRequest(
                    order = listOf(MangaDexSortedOrder(MangaListRequest.Order.FollowedCount)),
                    createdAtSince = Clock.System.now().minus(1, DateTimeUnit.MONTH, TimeZone.UTC),
                    limit = 10
                ),
                withStatistics = true
            ).getOrThrow()

            val newRelease = mangaCentralRepository.minMangaList(
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
                newRelease = newRelease,
                newPopuler = newPopular,
                popularThisYear = popularThisYear,
                latestUpdates = latestUpdates
            )
        }
    }

}

internal sealed interface HomeUiState {
    data class Success(
        val newRelease: List<MinManga>,
        val newPopuler: List<MinManga>,
        val popularThisYear: List<MinManga>,
        val latestUpdates: List<MinManga>
    ) : HomeUiState

    data object Loading : HomeUiState

    data class Failure(val message: String) : HomeUiState
}
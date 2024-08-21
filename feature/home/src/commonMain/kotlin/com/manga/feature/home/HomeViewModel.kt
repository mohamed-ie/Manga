package com.manga.feature.home

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manga.core.common.onSuccess
import com.manga.core.data.repository.manga.MangaRepository
import com.manga.core.data.repository.manga_central.MangaCentralRepository
import com.manga.core.model.manga.Manga
import com.manga.core.model.manga.MangaDexMangaOrder
import com.manga.core.model.manga.NewReleaseManga
import com.manga.core.model.manga.asNewReleaseManga
import com.manga.core.model.manga.request.MangaListRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class HomeViewModel(
    private val mangaCentralRepository: MangaCentralRepository,
    private val mangaRepository: MangaRepository
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
            mangaCentralRepository.newReleaseManga(
                request = MangaListRequest(
                    order = listOf(MangaDexMangaOrder(MangaDexMangaOrder.Order.CREATED_AT)),
                    limit = 6
                )
            ).onSuccess {
                _uiState.value = HomeUiState.Success(it)
            }
        }
    }

    val latestUpdatesManga = mangaCentralRepository.minMangaList(
        mangaListRequest = MangaListRequest(
            order = listOf(
                MangaDexMangaOrder(MangaDexMangaOrder.Order.LATEST_UPLOADED_CHAPTER)
            ),
            limit = 20
        )
    )

    val hightestRatingManga = mangaCentralRepository.minMangaList(
        mangaListRequest = MangaListRequest(
            order = listOf(MangaDexMangaOrder(MangaDexMangaOrder.Order.RATING)),
            limit = 20
        )
    )

    val recentlyAddedManga = mangaCentralRepository.minMangaList(
        mangaListRequest = MangaListRequest(
            order = listOf(MangaDexMangaOrder(MangaDexMangaOrder.Order.CREATED_AT)),
            limit = 20
        )
    )

}

internal sealed interface HomeUiState {
    data class Success(
        val newReleases: List<NewReleaseManga>,
    ) : HomeUiState

    data object Loading : HomeUiState

    data class Failure(val message: String) : HomeUiState
}
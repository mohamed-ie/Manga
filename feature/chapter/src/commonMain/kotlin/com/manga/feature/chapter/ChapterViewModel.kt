package com.manga.feature.chapter

import androidx.annotation.MainThread
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manga.core.common.onSuccess
import com.manga.core.data.repository.at_home.AtHomeRepository
import com.manga.core.model.manga_dex.at_home.AtHomeChapter
import com.manga.core.model.manga_dex.at_home.request.AtHomeServerChapterRequest
import com.manga.core.model.manga_dex.chapter.MinChapter
import com.manga.core.model.manga_dex.manga.MinManga
import com.manga.core.ui.*
import com.manga.feature.chapter.navigation.ARG_CHAPTER_ID
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class ChapterViewModel(
    savedStateHandle: SavedStateHandle,
    private val atHomeRepository: AtHomeRepository
) : ViewModel() {
    private val args = argsFromSavedStateHandle(savedStateHandle)

    private val _uiEvent = MutableSharedFlow<ChapterUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _uiState = MutableStateFlow<ChapterUiState>(ChapterUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private var initializeCalled = false

    @MainThread
    fun initialize() {
        if (initializeCalled) return
        initializeCalled = true
        initialLoad()
    }

    private fun initialLoad() {
        viewModelScope.launch {
            atHomeRepository.chapter(AtHomeServerChapterRequest(args.chapterId))
                .onSuccess {
                    _uiState.value = ChapterUiState.Success(it)
                }.onFailure {
                    _uiState.value = ChapterUiState.Failure(it)
                }
        }
    }
}

internal sealed interface ChapterUiState : SuccessUiState<ChapterUiState.Success> {
    data class Success(
        val chapter: AtHomeChapter
    ) : ChapterUiState

    data object Loading : ChapterUiState

    data class Failure(val message: UiText) : ChapterUiState
}

internal sealed interface ChapterEvent {
    data object Retry : ChapterEvent
    data object Refresh : ChapterEvent
    data class OpenChapter(val chapter: MinChapter) : ChapterEvent
    data class OpenManga(val manga: MinManga) : ChapterEvent
    data class OpenMangaList(val listIndex: Int) : ChapterEvent
}

internal sealed interface ChapterUiEvent {
    data class ShowSnackbar(
        override val message: UiText,
        override val actionLabel: UiText? = null,
        override val action: (() -> Unit)? = null
    ) : ChapterUiEvent, ScreenUiEvent.ShowSnackbarAction
}

internal data class ChapterArgs(val chapterId: String)

private fun argsFromSavedStateHandle(savedStateHandle: SavedStateHandle) = ChapterArgs(
    chapterId = checkNotNull(savedStateHandle[ARG_CHAPTER_ID])
)
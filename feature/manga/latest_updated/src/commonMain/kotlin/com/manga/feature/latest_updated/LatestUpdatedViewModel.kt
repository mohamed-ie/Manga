package com.manga.feature.latest_updated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.manga.core.data.repository.manga_central.MangaCentralRepository
import com.manga.core.model.chapter.MinChapter
import com.manga.core.model.chapter.request.ChapterListRequest
import com.manga.core.model.common.descOrder
import com.manga.core.model.manga.MinManga
import com.manga.core.ui.distinctBy
import com.manga.core.ui.offsetPagerOf
import kotlinx.coroutines.flow.map
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class LatestUpdatedViewModel(
    private val mangaCentralRepository: MangaCentralRepository
) : ViewModel() {

    val manga = offsetPagerOf {
        mangaCentralRepository.minMangaList(
            ChapterListRequest(
                offset = it ?: 0,
                limit = 100,
                order = listOf(ChapterListRequest.Order.ReadableAt.descOrder)
            )
        )
    }
        .flow
        .cachedIn(viewModelScope)
        .map { it.distinctBy { minManga -> minManga.lastChapter?.id ?: minManga.id } }
}

internal sealed interface LatestUpdatedEvent {
    data class OpenManga(val minManga: MinManga) : LatestUpdatedEvent
    data class OpenChapter(val minChapter: MinChapter) : LatestUpdatedEvent
}
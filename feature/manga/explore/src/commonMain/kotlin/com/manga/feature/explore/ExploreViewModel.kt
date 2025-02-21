package com.manga.feature.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manga.core.data.provider.FilterDataProvider
import com.manga.core.data.provider.request_provider.RequestProvider
import com.manga.core.data.repository.manga.MangaRepository
import com.manga.core.ui.pagerFlowOf
import com.manga.core.ui.utils.distinctBy
import kotlinx.coroutines.flow.map
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class ExploreViewModel(
    private val mangaRepository: MangaRepository,
    private val requestProvider: RequestProvider,
    private val filterDataProvider: FilterDataProvider
) : ViewModel(), RequestProvider by requestProvider {

    val manga = pagerFlowOf(
        scope = viewModelScope,
        request = { mangaRepository.mangaList(request("explore"), nextKey = it) }
    ).map { it.distinctBy { minManga -> minManga.lastChapter?.id ?: minManga.id } }
}
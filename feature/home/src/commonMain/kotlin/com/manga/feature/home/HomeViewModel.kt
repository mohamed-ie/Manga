package com.manga.feature.home

import androidx.lifecycle.ViewModel
import com.manga.core.data.repository.manga_central.MangaCentralRepository
import com.manga.core.model.manga.request.MangaListRequest
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class HomeViewModel(
    private val mangaRepository: MangaCentralRepository
) : ViewModel() {
    val manga = mangaRepository.minManga(mangaListRequest = MangaListRequest())
}
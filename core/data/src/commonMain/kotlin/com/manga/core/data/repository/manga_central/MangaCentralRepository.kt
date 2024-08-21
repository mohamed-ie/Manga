package com.manga.core.data.repository.manga_central

import androidx.paging.PagingData
import com.manga.core.model.manga.MinManga
import com.manga.core.model.manga.request.MangaListRequest
import kotlinx.coroutines.flow.Flow

interface MangaCentralRepository {
    fun minManga(mangaListRequest: MangaListRequest): Flow<PagingData<MinManga>>
}
package com.manga.core.data.repository.manga_central

import androidx.paging.PagingData
import com.manga.core.common.Resource
import com.manga.core.model.MangaException
import com.manga.core.model.manga.MinManga
import com.manga.core.model.manga.NewReleaseManga
import com.manga.core.model.manga.request.MangaListRequest
import kotlinx.coroutines.flow.Flow

interface MangaCentralRepository {
    fun minMangaList(mangaListRequest: MangaListRequest): Flow<PagingData<MinManga>>
    suspend fun newReleaseManga(request: MangaListRequest): Resource<List<NewReleaseManga>, MangaException>
}
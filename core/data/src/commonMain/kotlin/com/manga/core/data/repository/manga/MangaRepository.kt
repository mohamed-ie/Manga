package com.manga.core.data.repository.manga

import androidx.paging.PagingData
import com.manga.core.common.Resource
import com.manga.core.model.MangaException
import kotlinx.coroutines.flow.Flow
import com.manga.core.model.manga.Manga
import com.manga.core.model.manga.MangaVolume
import com.manga.core.model.manga.MinManga
import com.manga.core.model.manga.request.MangaChaptersRequest
import com.manga.core.model.manga.request.MangaListRequest
import com.manga.core.model.manga.request.MangaRequest
import com.manga.core.network.response.MangaDexErrorResponse

interface MangaRepository {
    fun mangaList(request: MangaListRequest = MangaListRequest()): Flow<PagingData<MinManga>>
    suspend fun manga(request: MangaRequest): Resource<Manga, MangaException>
    suspend fun chapters(request: MangaChaptersRequest): Resource<List<MangaVolume>, MangaException>
}


package com.manga.core.data.repository.manga

import com.manga.core.common.Resource
import com.manga.core.model.manga_dex.common.MangaException
import com.manga.core.model.manga_dex.common.MangaDexManga
import com.manga.core.model.manga_dex.manga.MangaVolume
import com.manga.core.model.manga_dex.manga.request.MangaChaptersRequest
import com.manga.core.model.manga_dex.manga.request.MangaListRequest
import com.manga.core.model.manga_dex.manga.request.MangaRequest
import com.manga.core.common.Pageable

interface MangaRepository {
    suspend fun mangaList(request: MangaListRequest = MangaListRequest()): Resource<Pageable<MangaDexManga, Int>, MangaException>
    suspend fun manga(request: MangaRequest): Resource<MangaDexManga?, MangaException>
    suspend fun chapters(request: MangaChaptersRequest): Resource<List<MangaVolume>, MangaException>
}


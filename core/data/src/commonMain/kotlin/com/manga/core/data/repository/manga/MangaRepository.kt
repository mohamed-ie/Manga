package com.manga.core.data.repository.manga

import com.manga.core.common.Resource
import com.manga.core.model.MangaException
import com.manga.core.model.manga.Manga
import com.manga.core.model.manga.MangaVolume
import com.manga.core.model.manga.request.MangaChaptersRequest
import com.manga.core.model.manga.request.MangaListRequest
import com.manga.core.model.manga.request.MangaRequest
import core.common.com.manga.core.common.Pageable

interface MangaRepository {
    suspend fun mangaList(request: MangaListRequest = MangaListRequest()): Resource<Pageable<Manga, Int>, MangaException>
    suspend fun manga(request: MangaRequest): Resource<Manga, MangaException>
    suspend fun chapters(request: MangaChaptersRequest): Resource<List<MangaVolume>, MangaException>
}


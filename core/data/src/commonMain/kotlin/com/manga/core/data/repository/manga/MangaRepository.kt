package com.manga.core.data.repository.manga

import com.manga.core.common.Pageable
import com.manga.core.common.Resource
import com.manga.core.model.common.MangaException
import com.manga.core.model.manga.Manga
import com.manga.core.model.request.Request

interface MangaRepository {
    suspend fun manga(request: Request): Resource<Manga?, MangaException>
    suspend fun mangaList(request: Request = Request(),nextKey: Int? = null): Resource<Pageable<Manga, Int>, MangaException>
}
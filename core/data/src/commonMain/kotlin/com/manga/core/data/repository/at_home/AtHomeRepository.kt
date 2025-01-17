package com.manga.core.data.repository.at_home

import com.manga.core.model.manga_dex.common.MangaException
import com.manga.core.model.manga_dex.at_home.AtHomeChapter
import com.manga.core.model.manga_dex.at_home.request.AtHomeServerChapterRequest
import com.manga.core.common.Resource

interface AtHomeRepository {
    suspend fun chapter(request: AtHomeServerChapterRequest): Resource<AtHomeChapter, MangaException>
}


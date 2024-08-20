package com.manga.core.data.repository.at_home

import com.manga.core.model.MangaException
import com.manga.core.model.at_home.AtHomeChapter
import com.manga.core.model.at_home.request.AtHomeServerChapterRequest
import com.manga.core.common.Resource

interface AtHomeRepository {
    suspend fun chapter(request: AtHomeServerChapterRequest): Resource<AtHomeChapter, MangaException>
}


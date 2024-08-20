package com.manga.core.data.repository.chapter

import com.manga.core.model.MangaException
import com.manga.core.model.chapter.Chapter
import com.manga.core.model.chapter.request.ChapterRequest
import com.manga.core.common.Resource

interface ChapterRepository {
    suspend fun chapter(request: ChapterRequest): Resource<Chapter, MangaException>
}
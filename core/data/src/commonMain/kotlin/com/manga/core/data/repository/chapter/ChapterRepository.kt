package com.manga.core.data.repository.chapter

import androidx.paging.PagingData
import com.manga.core.common.Resource
import com.manga.core.model.MangaException
import com.manga.core.model.chapter.Chapter
import com.manga.core.model.chapter.request.ChapterListRequest
import com.manga.core.model.chapter.request.ChapterRequest
import core.common.com.manga.core.common.Pageable
import kotlinx.coroutines.flow.Flow

interface ChapterRepository {
    suspend fun chapterList(request: ChapterListRequest): Resource<Pageable<Chapter, Int>, MangaException>
    suspend fun chapter(request: ChapterRequest): Resource<Chapter, MangaException>
}
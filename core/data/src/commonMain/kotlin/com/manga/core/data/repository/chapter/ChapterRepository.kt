package com.manga.core.data.repository.chapter

import com.manga.core.common.Resource
import com.manga.core.model.MangaException
import com.manga.core.model.chapter.MangaDexChapter
import com.manga.core.model.chapter.request.ChapterListRequest
import com.manga.core.model.chapter.request.ChapterRequest
import core.common.com.manga.core.common.Pageable

interface ChapterRepository {
    suspend fun chapterList(request: ChapterListRequest): Resource<Pageable<MangaDexChapter, Int>, MangaException>
    suspend fun chapter(request: ChapterRequest): Resource<MangaDexChapter, MangaException>
}
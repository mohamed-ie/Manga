package com.manga.core.network.datasource

import com.manga.core.common.Resource
import com.manga.core.model.chapter.request.ChapterListRequest
import com.manga.core.model.chapter.request.ChapterRequest
import com.manga.core.network.response.MangaDexErrorResponse
import com.manga.core.network.response.chapter.ChapterListResponse
import com.manga.core.network.response.chapter.ChapterResponse

interface MangaDexChapterNetworkDataSource {
    suspend fun chapterList(request: ChapterListRequest): Resource<ChapterListResponse, MangaDexErrorResponse>
    suspend fun chapter(request: ChapterRequest): Resource<ChapterResponse, MangaDexErrorResponse>
}
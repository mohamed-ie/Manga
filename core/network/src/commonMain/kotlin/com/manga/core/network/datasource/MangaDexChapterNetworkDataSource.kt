package com.manga.core.network.datasource

import com.manga.core.common.Resource
import com.manga.core.model.manga_dex.chapter.request.ChapterListRequest
import com.manga.core.model.manga_dex.chapter.request.ChapterRequest
import com.manga.core.network.manga_dex.model.MangaDexErrorNetworkModel
import com.manga.core.network.manga_dex.model.chapter.ChapterListNetworkModel
import com.manga.core.network.manga_dex.model.chapter.ChapterNetworkModel
import com.manga.core.network.manga_dex.model.common.MangaDexNetworkModel
import com.manga.core.network.manga_dex.model.common.MangaDexPageable
import com.manga.core.network.manga_dex.model.common.relationships.ChapterRelationship

interface MangaDexChapterNetworkDataSource {
    suspend fun chapterList(request: ChapterListRequest): Resource<MangaDexPageable<ChapterRelationship>, MangaDexErrorNetworkModel>
    suspend fun chapter(request: ChapterRequest): Resource<MangaDexNetworkModel<ChapterRelationship>, MangaDexErrorNetworkModel>
}
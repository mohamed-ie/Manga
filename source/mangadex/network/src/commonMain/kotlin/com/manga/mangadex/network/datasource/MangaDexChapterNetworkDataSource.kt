package com.manga.mangadex.network.datasource

import com.manga.core.common.Resource
import com.manga.core.model.request.Request
import com.manga.mangadex.network.model.MangaDexErrorNetworkModel
import com.manga.mangadex.network.model.common.MangaDexNetworkModel
import com.manga.mangadex.network.model.common.MangaDexPageable
import com.manga.mangadex.network.model.common.relationships.ChapterRelationship

interface MangaDexChapterNetworkDataSource {
    suspend fun chapterList(request: Request): Resource<MangaDexPageable<ChapterRelationship>, MangaDexErrorNetworkModel>
    suspend fun chapter(request: Request): Resource<MangaDexNetworkModel<ChapterRelationship>, MangaDexErrorNetworkModel>
}
package com.manga.core.data.repository.chapter

import com.manga.core.common.Resource
import com.manga.core.common.map
import com.manga.core.common.mapExpected
import com.manga.core.model.MangaException
import com.manga.core.model.chapter.Chapter
import com.manga.core.model.chapter.request.ChapterListRequest
import com.manga.core.model.chapter.request.ChapterRequest
import com.manga.core.network.datasource.MangaDexChapterNetworkDataSource
import com.manga.core.network.response.asExternalModel
import com.manga.core.network.response.chapter.asExternalModel
import com.manga.core.network.response.chapter.asPageable
import core.common.com.manga.core.common.Pageable
import org.koin.core.annotation.Single

@Single
internal class ChapterRepositoryImpl(
    private val dataSource: MangaDexChapterNetworkDataSource
) : ChapterRepository {
    override suspend fun chapterList(request: ChapterListRequest): Resource<Pageable<Chapter, Int>, MangaException> =
        dataSource.chapterList(request)
            .map { it.asPageable() }
            .mapExpected { it.asExternalModel() }

    override suspend fun chapter(request: ChapterRequest): Resource<Chapter, MangaException> =
        dataSource.chapter(request)
            .mapExpected { it.asExternalModel() }
            .map {
                it.asExternalModel()
                    ?: throw MangaException("No chapter found with ${request.id} id")
            }
}


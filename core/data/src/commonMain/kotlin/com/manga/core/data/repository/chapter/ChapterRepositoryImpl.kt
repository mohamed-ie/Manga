package com.manga.core.data.repository.chapter

import com.manga.core.common.Resource
import com.manga.core.common.map
import com.manga.core.common.mapExpected
import com.manga.core.model.manga_dex.common.MangaException
import com.manga.core.model.manga_dex.chapter.MangaDexChapter
import com.manga.core.model.manga_dex.chapter.request.ChapterListRequest
import com.manga.core.model.manga_dex.chapter.request.ChapterRequest
import com.manga.core.network.datasource.MangaDexChapterNetworkDataSource
import com.manga.core.network.manga_dex.model.chapter.asPageable
import com.manga.core.common.Pageable
import com.manga.core.network.manga_dex.model.asExternalModel
import com.manga.core.network.manga_dex.model.chapter.ChapterNetworkModel
import com.manga.core.network.manga_dex.model.common.asPageable
import com.manga.core.network.manga_dex.model.common.relationships.ChapterRelationship
import com.manga.core.network.manga_dex.model.common.relationships.asExternalModel
import org.koin.core.annotation.Single

@Single
internal class ChapterRepositoryImpl(
    private val dataSource: MangaDexChapterNetworkDataSource
) : ChapterRepository {
    override suspend fun chapterList(request: ChapterListRequest): Resource<Pageable<MangaDexChapter, Int>, MangaException> =
        dataSource.chapterList(request)
            .mapExpected { it.asExternalModel() }
            .map { it.asPageable(ChapterRelationship::asExternalModel) }

    override suspend fun chapter(request: ChapterRequest): Resource<MangaDexChapter?, MangaException> =
        dataSource.chapter(request)
            .mapExpected { it.asExternalModel() }
            .map { it.data?.asExternalModel() }
}


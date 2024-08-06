package chapter

import MangaException
import chapter.request.ChapterRequest
import core.common.Resource
import core.common.map
import core.common.mapExpected
import datasource.MangaDexChapterNetworkDataSource
import org.koin.core.annotation.Single
import response.asExternalModel
import response.chapter.asExternalModel

@Single
internal class ChapterRepositoryImpl(
    private val dataSource: MangaDexChapterNetworkDataSource
) : ChapterRepository {
    override suspend fun chapter(request: ChapterRequest): Resource<Chapter, MangaException> =
        dataSource.chapter(request)
            .mapExpected { it.asExternalModel() }
            .map { it.asExternalModel() ?: throw MangaException("No chapter found with ${request.id} id") }
}
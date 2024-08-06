package at_home

import MangaException
import at_home.request.AtHomeServerChapterRequest
import core.common.Resource
import core.common.map
import core.common.mapExpected
import datasource.MangaDexAtHomeNetworkDataSource
import org.koin.core.annotation.Single
import response.asExternalModel
import response.at_home.asExternalModel

@Single
internal class AtHomeRepositoryImpl(
    private val dataSource: MangaDexAtHomeNetworkDataSource
) : AtHomeRepository {
    override suspend fun chapter(request: AtHomeServerChapterRequest): Resource<AtHomeChapter, MangaException> =
        dataSource.chapter(request)
            .map { it.asExternalModel() ?: throw MangaException("No chapter found with ${request.id} id") }
            .mapExpected { it.asExternalModel() }
}
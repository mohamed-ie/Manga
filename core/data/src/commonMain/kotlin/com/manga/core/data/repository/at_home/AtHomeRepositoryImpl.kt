package com.manga.core.data.repository.at_home

import com.manga.core.model.MangaException
import com.manga.core.model.at_home.AtHomeChapter
import com.manga.core.model.at_home.request.AtHomeServerChapterRequest
import com.manga.core.common.Resource
import com.manga.core.common.map
import com.manga.core.common.mapExpected
import com.manga.core.network.datasource.MangaDexAtHomeNetworkDataSource
import org.koin.core.annotation.Single
import com.manga.core.network.response.asExternalModel
import com.manga.core.network.response.at_home.asExternalModel

@Single
internal class AtHomeRepositoryImpl(
    private val dataSource: MangaDexAtHomeNetworkDataSource
) : AtHomeRepository {
    override suspend fun chapter(request: AtHomeServerChapterRequest): Resource<AtHomeChapter, MangaException> =
        dataSource.chapter(request)
            .map { it.asExternalModel() ?: throw MangaException("No chapter found with ${request.id} id") }
            .mapExpected { it.asExternalModel() }
}
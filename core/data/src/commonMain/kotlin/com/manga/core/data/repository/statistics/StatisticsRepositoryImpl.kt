package com.manga.core.data.repository.statistics

import com.manga.core.model.MangaException
import com.manga.core.common.Resource
import com.manga.core.common.map
import com.manga.core.common.mapExpected
import com.manga.core.network.datasource.MangaDexStatisticsNetworkDataSource
import org.koin.core.annotation.Single
import com.manga.core.network.response.asExternalModel
import com.manga.core.network.response.statistics.asExternalModel
import com.manga.core.model.statistics.MangaStatistics
import com.manga.core.model.statistics.request.MangaStatisticsRequest

@Single
internal class StatisticsRepositoryImpl(
    private val dataSource: MangaDexStatisticsNetworkDataSource
) : StatisticsRepository {
    override suspend fun manga(request: MangaStatisticsRequest): Resource<MangaStatistics, MangaException> =
        dataSource.manga(request)
            .map { it.asExternalModel() ?: throw MangaException("no statistics for manga with ${request.id} id") }
            .mapExpected { it.asExternalModel() }
}
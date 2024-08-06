package statistics

import MangaException
import core.common.Resource
import core.common.map
import core.common.mapExpected
import datasource.MangaDexStatisticsNetworkDataSource
import org.koin.core.annotation.Single
import response.asExternalModel
import response.statistics.asExternalModel
import statistics.request.MangaStatisticsRequest

@Single
internal class StatisticsRepositoryImpl(
    private val dataSource: MangaDexStatisticsNetworkDataSource
) : StatisticsRepository {
    override suspend fun manga(request: MangaStatisticsRequest): Resource<MangaStatistics, MangaException> =
        dataSource.manga(request)
            .map { it.asExternalModel() ?: throw MangaException("no statistics for manga with ${request.id} id") }
            .mapExpected { it.asExternalModel() }
}
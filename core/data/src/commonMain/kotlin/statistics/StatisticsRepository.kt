package statistics

import MangaException
import core.common.Resource
import statistics.request.MangaStatisticsRequest

interface StatisticsRepository {
    suspend fun manga(request: MangaStatisticsRequest): Resource<MangaStatistics, MangaException>
}
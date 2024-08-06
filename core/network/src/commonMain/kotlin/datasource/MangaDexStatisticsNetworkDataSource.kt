package datasource

import core.common.Resource
import response.MangaDexErrorResponse
import response.statistics.MangaStatisticsResponse
import statistics.request.MangaStatisticsRequest

interface MangaDexStatisticsNetworkDataSource {
    suspend fun manga(request: MangaStatisticsRequest): Resource<MangaStatisticsResponse, MangaDexErrorResponse>
}
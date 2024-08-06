package ktor.datasource

import core.common.Resource
import datasource.MangaDexStatisticsNetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import ktor.apiCall
import org.koin.core.annotation.Single
import response.MangaDexErrorResponse
import response.statistics.MangaStatisticsResponse
import statistics.request.MangaStatisticsRequest

@Single
internal class KtorMangaDexStatisticsNetwork(
    private val client: HttpClient
) : MangaDexStatisticsNetworkDataSource {
    override suspend fun manga(request: MangaStatisticsRequest): Resource<MangaStatisticsResponse, MangaDexErrorResponse> =
        client.apiCall {
            get("statistics/manga/${request.id}")
        }
}
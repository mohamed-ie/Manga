package com.manga.core.network.ktor.datasource

import com.manga.core.common.Resource
import com.manga.core.network.datasource.MangaDexStatisticsNetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import com.manga.core.network.ktor.apiCall
import org.koin.core.annotation.Single
import com.manga.core.network.response.MangaDexErrorResponse
import com.manga.core.network.response.statistics.MangaStatisticsResponse
import com.manga.core.model.statistics.request.MangaStatisticsRequest

@Single
internal class KtorMangaDexStatisticsNetwork(
    private val client: HttpClient
) : MangaDexStatisticsNetworkDataSource {
    override suspend fun manga(request: MangaStatisticsRequest): Resource<MangaStatisticsResponse, MangaDexErrorResponse> =
        client.apiCall {
            get("statistics/manga/${request.id}")
        }
}
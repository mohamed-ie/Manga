package com.manga.core.network.ktor.datasource

import com.manga.core.common.Resource
import com.manga.core.model.statistics.request.MangaListStatisticsRequest
import com.manga.core.network.datasource.MangaDexStatisticsNetworkDataSource
import io.ktor.client.HttpClient
import com.manga.core.network.ktor.apiCall
import org.koin.core.annotation.Single
import com.manga.core.network.response.MangaDexErrorResponse
import com.manga.core.network.response.statistics.MangaStatisticsResponse
import com.manga.core.model.statistics.request.MangaStatisticsRequest
import io.ktor.client.request.*

@Single
internal class KtorMangaDexStatisticsNetwork(
    private val client: HttpClient
) : MangaDexStatisticsNetworkDataSource {
    override suspend fun manga(request: MangaStatisticsRequest): Resource<MangaStatisticsResponse, MangaDexErrorResponse> =
        client.apiCall {
            get("statistics/manga/${request.id}")
        }

    override suspend fun mangaList(request: MangaListStatisticsRequest): Resource<MangaStatisticsResponse, MangaDexErrorResponse> =
        client.apiCall {
            get("statistics/manga") {
                request.ids?.forEach { parameter("manga[]", it) }
            }
        }

}
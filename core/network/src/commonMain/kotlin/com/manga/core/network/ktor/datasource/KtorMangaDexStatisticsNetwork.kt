package com.manga.core.network.ktor.datasource

import com.manga.core.common.Resource
import com.manga.core.model.manga_dex.statistics.request.MangaListStatisticsRequest
import com.manga.core.network.datasource.MangaDexStatisticsNetworkDataSource
import io.ktor.client.HttpClient
import com.manga.core.network.ktor.apiCall
import org.koin.core.annotation.Single
import com.manga.core.network.manga_dex.model.MangaDexErrorNetworkModel
import com.manga.core.network.manga_dex.model.statistics.MangaStatisticsResponse
import com.manga.core.model.manga_dex.statistics.request.MangaStatisticsRequest
import io.ktor.client.request.*

@Single
internal class KtorMangaDexStatisticsNetwork(
    private val client: HttpClient
) : MangaDexStatisticsNetworkDataSource {
    override suspend fun manga(request: MangaStatisticsRequest): Resource<MangaStatisticsResponse, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("statistics/manga/${request.id}")
        }

    override suspend fun mangaList(request: MangaListStatisticsRequest): Resource<MangaStatisticsResponse, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("statistics/manga") {
                request.ids?.forEach { parameter("manga[]", it) }
            }
        }

}
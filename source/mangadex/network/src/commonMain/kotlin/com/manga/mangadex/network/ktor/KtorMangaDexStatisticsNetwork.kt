package com.manga.mangadex.network.ktor

import com.manga.core.common.Resource
import com.manga.core.model.request.Request
import com.manga.core.network.ktor.apiCall
import com.manga.core.network.ktor.parametersOf
import com.manga.mangadex.network.datasource.MangaDexStatisticsNetworkDataSource
import com.manga.mangadex.network.model.MangaDexErrorNetworkModel
import com.manga.mangadex.network.model.serverDateTimeFormat
import com.manga.mangadex.network.model.statistics.MangaStatisticsResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.datetime.Instant
import org.koin.core.annotation.Single

@Single
internal class KtorMangaDexStatisticsNetwork(
    private val client: HttpClient
) : MangaDexStatisticsNetworkDataSource {
    override suspend fun manga(request: Request): Resource<MangaStatisticsResponse, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("statistics/manga/${request["id"]}")
        }

    override suspend fun mangaList(request: Request): Resource<MangaStatisticsResponse, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("statistics/manga") {
                parametersOf(
                    request = request,
                    dateFormat = Instant::serverDateTimeFormat
                )            }
        }
}
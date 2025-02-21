package com.manga.mangadex.network.ktor

import com.manga.core.common.Resource
import com.manga.core.model.request.Request
import com.manga.core.network.ktor.apiCall
import com.manga.core.network.ktor.parametersOf
import com.manga.mangadex.network.datasource.MangaDexMangaNetworkDataSource
import com.manga.mangadex.network.model.MangaDexErrorNetworkModel
import com.manga.mangadex.network.model.common.MangaDexPageable
import com.manga.mangadex.network.model.common.relationships.MangaRelationship
import com.manga.mangadex.network.model.manga.MangaChaptersNetworkModel
import com.manga.mangadex.network.model.manga.MangaNetworkModel
import com.manga.mangadex.network.model.serverDateTimeFormat
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.datetime.Instant
import org.koin.core.annotation.Single

@Single
internal class KtorMangaDexMangaNetwork(
    private val client: HttpClient
) : MangaDexMangaNetworkDataSource {
    override suspend fun mangaList(
        request: Request
    ): Resource<MangaDexPageable<MangaRelationship>, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("manga") {
                parametersOf(
                    request = request,
                    dateFormat = Instant::serverDateTimeFormat
                )
            }
        }

    override suspend fun manga(request: Request): Resource<MangaNetworkModel, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("manga/${request}") {
                parametersOf(
                    request = request,
                    dateFormat = Instant::serverDateTimeFormat
                )
            }
        }

    override suspend fun chapters(request: Request): Resource<MangaChaptersNetworkModel, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("manga/${request["id"]}/aggregate") {
                parametersOf(
                    request = request,
                    dateFormat = Instant::serverDateTimeFormat
                )
            }
        }
}
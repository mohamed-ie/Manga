package com.manga.mangadex.network.ktor

import com.manga.core.common.Resource
import com.manga.core.model.request.Request
import com.manga.core.network.ktor.apiCall
import com.manga.mangadex.network.model.MangaDexErrorNetworkModel
import com.manga.mangadex.network.model.at_home.AtHomeServerChapterNetworkModel
import com.manga.mangadex.network.datasource.MangaDexAtHomeNetworkDataSource
import io.ktor.client.*
import io.ktor.client.request.*
import org.koin.core.annotation.Single

@Single
internal class KtorMangaDexAtHomeNetwork(
    private val client: HttpClient
) : MangaDexAtHomeNetworkDataSource {
    override suspend fun chapter(request: Request): Resource<AtHomeServerChapterNetworkModel, MangaDexErrorNetworkModel> =
        client.apiCall { get("at-home/server/${request["id"]}") }
}
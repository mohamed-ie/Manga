package com.manga.core.network.ktor.datasource

import com.manga.core.model.manga_dex.at_home.request.AtHomeServerChapterRequest
import com.manga.core.common.Resource
import com.manga.core.network.datasource.MangaDexAtHomeNetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import com.manga.core.network.ktor.apiCall
import org.koin.core.annotation.Single
import com.manga.core.network.manga_dex.model.MangaDexErrorNetworkModel
import com.manga.core.network.manga_dex.model.at_home.AtHomeServerChapterNetworkModel

@Single
internal class KtorMangaDexAtHomeNetwork(private val client: HttpClient) :
    MangaDexAtHomeNetworkDataSource {
    override suspend fun chapter(request: AtHomeServerChapterRequest): Resource<AtHomeServerChapterNetworkModel, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("at-home/server/${request.id}")
        }
}
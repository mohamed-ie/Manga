package com.manga.core.network.ktor.datasource

import com.manga.core.model.at_home.request.AtHomeServerChapterRequest
import com.manga.core.common.Resource
import com.manga.core.network.datasource.MangaDexAtHomeNetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import com.manga.core.network.ktor.apiCall
import org.koin.core.annotation.Single
import com.manga.core.network.response.MangaDexErrorResponse
import com.manga.core.network.response.at_home.AtHomeServerChapterResponse

@Single
internal class KtorMangaDexAtHomeNetwork(private val client: HttpClient) :
    MangaDexAtHomeNetworkDataSource {
    override suspend fun chapter(request: AtHomeServerChapterRequest): Resource<AtHomeServerChapterResponse, MangaDexErrorResponse> =
        client.apiCall {
            get("at-home/server/${request.id}")
        }
}
package ktor.datasource

import at_home.request.AtHomeServerChapterRequest
import core.common.Resource
import datasource.MangaDexAtHomeNetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import ktor.apiCall
import org.koin.core.annotation.Single
import response.MangaDexErrorResponse
import response.at_home.AtHomeServerChapterResponse

@Single
internal class KtorMangaDexAtHomeNetwork(private val client: HttpClient) : MangaDexAtHomeNetworkDataSource {
    override suspend fun chapter(request: AtHomeServerChapterRequest): Resource<AtHomeServerChapterResponse, MangaDexErrorResponse> =
        client.apiCall {
            get("at-home/server/${request.id}")
        }
}
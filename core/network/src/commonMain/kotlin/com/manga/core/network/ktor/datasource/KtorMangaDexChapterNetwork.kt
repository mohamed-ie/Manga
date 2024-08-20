package com.manga.core.network.ktor.datasource

import com.manga.core.model.chapter.request.ChapterRequest
import com.manga.core.common.Resource
import com.manga.core.network.datasource.MangaDexChapterNetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import com.manga.core.network.ktor.apiCall
import org.koin.core.annotation.Single
import com.manga.core.network.response.MangaDexErrorResponse
import com.manga.core.network.response.chapter.ChapterResponse

@Single
internal class KtorMangaDexChapterNetwork(
    private val client: HttpClient
) : MangaDexChapterNetworkDataSource {
    override suspend fun chapter(request: ChapterRequest): Resource<ChapterResponse, MangaDexErrorResponse> =
        client.apiCall {
            get("chapter/${request.id}") {
                request.includes?.forEach { parameter("includes[]", it) }
            }
        }
}
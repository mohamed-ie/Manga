package ktor.datasource

import chapter.request.ChapterRequest
import core.common.Resource
import datasource.MangaDexChapterNetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import ktor.apiCall
import org.koin.core.annotation.Single
import response.MangaDexErrorResponse
import response.chapter.ChapterResponse

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
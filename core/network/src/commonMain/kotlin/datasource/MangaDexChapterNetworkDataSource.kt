package datasource

import chapter.request.ChapterRequest
import core.common.Resource
import response.MangaDexErrorResponse
import response.chapter.ChapterResponse

interface MangaDexChapterNetworkDataSource {
    suspend fun chapter(request: ChapterRequest): Resource<ChapterResponse, MangaDexErrorResponse>
}
package datasource

import at_home.request.AtHomeServerChapterRequest
import core.common.Resource
import response.MangaDexErrorResponse
import response.at_home.AtHomeServerChapterResponse

interface MangaDexAtHomeNetworkDataSource {
    suspend fun chapter(request: AtHomeServerChapterRequest): Resource<AtHomeServerChapterResponse, MangaDexErrorResponse>
}
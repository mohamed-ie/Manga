package com.manga.core.network.datasource

import com.manga.core.model.at_home.request.AtHomeServerChapterRequest
import com.manga.core.common.Resource
import com.manga.core.network.response.MangaDexErrorResponse
import com.manga.core.network.response.at_home.AtHomeServerChapterResponse

interface MangaDexAtHomeNetworkDataSource {
    suspend fun chapter(request: AtHomeServerChapterRequest): Resource<AtHomeServerChapterResponse, MangaDexErrorResponse>
}
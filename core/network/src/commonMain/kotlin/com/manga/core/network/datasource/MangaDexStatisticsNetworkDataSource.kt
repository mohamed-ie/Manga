package com.manga.core.network.datasource

import com.manga.core.common.Resource
import com.manga.core.network.response.MangaDexErrorResponse
import com.manga.core.network.response.statistics.MangaStatisticsResponse
import com.manga.core.model.statistics.request.MangaStatisticsRequest

interface MangaDexStatisticsNetworkDataSource {
    suspend fun manga(request: MangaStatisticsRequest): Resource<MangaStatisticsResponse, MangaDexErrorResponse>
}
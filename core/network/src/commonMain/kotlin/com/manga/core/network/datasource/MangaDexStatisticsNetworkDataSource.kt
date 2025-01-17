package com.manga.core.network.datasource

import com.manga.core.common.Resource
import com.manga.core.model.manga_dex.statistics.request.MangaListStatisticsRequest
import com.manga.core.network.manga_dex.model.MangaDexErrorNetworkModel
import com.manga.core.network.manga_dex.model.statistics.MangaStatisticsResponse
import com.manga.core.model.manga_dex.statistics.request.MangaStatisticsRequest

interface MangaDexStatisticsNetworkDataSource {
    suspend fun manga(request: MangaStatisticsRequest): Resource<MangaStatisticsResponse, MangaDexErrorNetworkModel>
    suspend fun mangaList(request: MangaListStatisticsRequest): Resource<MangaStatisticsResponse, MangaDexErrorNetworkModel>
}
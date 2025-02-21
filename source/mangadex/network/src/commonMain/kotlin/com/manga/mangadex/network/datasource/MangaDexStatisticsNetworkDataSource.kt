package com.manga.mangadex.network.datasource

import com.manga.core.common.Resource
import com.manga.core.model.request.Request
import com.manga.mangadex.network.model.MangaDexErrorNetworkModel
import com.manga.mangadex.network.model.statistics.MangaStatisticsResponse

interface MangaDexStatisticsNetworkDataSource {
    suspend fun manga(request: Request): Resource<MangaStatisticsResponse, MangaDexErrorNetworkModel>
    suspend fun mangaList(request: Request): Resource<MangaStatisticsResponse, MangaDexErrorNetworkModel>
}
package com.manga.core.data.repository.statistics

import com.manga.core.model.manga_dex.common.MangaException
import com.manga.core.common.Resource
import com.manga.core.model.manga_dex.statistics.MangaDexMangaStatistics
import com.manga.core.model.manga_dex.statistics.request.MangaListStatisticsRequest
import com.manga.core.model.manga_dex.statistics.request.MangaStatisticsRequest

interface StatisticsRepository {
    suspend fun manga(request: MangaStatisticsRequest): Resource<MangaDexMangaStatistics, MangaException>
    suspend fun mangaList(request: MangaListStatisticsRequest): Resource<List<MangaDexMangaStatistics>, MangaException>
}
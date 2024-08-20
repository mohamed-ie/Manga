package com.manga.core.data.repository.statistics

import com.manga.core.model.MangaException
import com.manga.core.common.Resource
import com.manga.core.model.statistics.MangaStatistics
import com.manga.core.model.statistics.request.MangaStatisticsRequest

interface StatisticsRepository {
    suspend fun manga(request: MangaStatisticsRequest): Resource<MangaStatistics, MangaException>
}
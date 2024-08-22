package com.manga.core.data.repository.statistics

import com.manga.core.common.Resource
import com.manga.core.common.map
import com.manga.core.common.mapExpected
import com.manga.core.model.MangaException
import com.manga.core.model.statistics.MangaDexMangaStatistics
import com.manga.core.model.statistics.request.MangaListStatisticsRequest
import com.manga.core.model.statistics.request.MangaStatisticsRequest
import com.manga.core.network.datasource.MangaDexStatisticsNetworkDataSource
import com.manga.core.network.response.asExternalModel
import com.manga.core.network.response.statistics.asMangaDexModelList
import org.koin.core.annotation.Single

@Single
internal class StatisticsRepositoryImpl(
    private val dataSource: MangaDexStatisticsNetworkDataSource
) : StatisticsRepository {
    override suspend fun manga(request: MangaStatisticsRequest): Resource<MangaDexMangaStatistics, MangaException> =
        dataSource.manga(request)
            .map { it.asMangaDexModelList[0] }
            .mapExpected { it.asExternalModel() }

    override suspend fun mangaList(request: MangaListStatisticsRequest): Resource<List<MangaDexMangaStatistics>, MangaException> =
        dataSource.mangaList(request)
            .map { it.asMangaDexModelList }
            .mapExpected { it.asExternalModel() }

}
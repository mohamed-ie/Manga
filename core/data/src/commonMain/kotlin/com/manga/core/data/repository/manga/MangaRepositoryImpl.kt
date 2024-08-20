package com.manga.core.data.repository.manga

import com.manga.core.model.MangaException
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.manga.core.common.Resource
import com.manga.core.common.map
import com.manga.core.common.mapExpected
import com.manga.core.network.datasource.MangaDexMangaNetworkDataSource
import kotlinx.coroutines.flow.Flow
import com.manga.core.model.manga.Manga
import com.manga.core.model.manga.MangaVolume
import com.manga.core.model.manga.MinManga
import com.manga.core.model.manga.request.MangaChaptersRequest
import com.manga.core.model.manga.request.MangaListRequest
import com.manga.core.model.manga.request.MangaRequest
import org.koin.core.annotation.Single
import com.manga.core.network.response.MangaDexErrorResponse
import com.manga.core.network.response.asExternalModel
import com.manga.core.network.response.manga.MangaChaptersResponse
import com.manga.core.network.response.manga.asExternalModel
import com.manga.core.network.response.manga.asManga

@Single
internal class MangaRepositoryImpl(
    private val mangaDataSource: MangaDexMangaNetworkDataSource
) : MangaRepository {
    override fun mangaList(request: MangaListRequest): Flow<PagingData<MinManga>> =
        Pager(
            config = PagingConfig(pageSize = request.limit),
            pagingSourceFactory = { MinMangePagingSource(mangaDataSource, request) }
        )
            .flow

    override suspend fun chapters(request: MangaChaptersRequest): Resource<List<MangaVolume>, MangaException> =
        mangaDataSource.chapters(request)
            .map {
                it.volumes
                    ?.values
                    ?.filterNotNull()
                    ?.map(MangaChaptersResponse.Volume::asExternalModel)
                    ?: emptyList()
            }
            .mapExpected { it.asExternalModel() }

    override suspend fun manga(request: MangaRequest): Resource<Manga, MangaException> =
        mangaDataSource.manga(request)
            .map { it.data?.asManga() ?: throw MangaException("Can not find manga with ${request.id} id") }
            .mapExpected { it.asExternalModel() }
}


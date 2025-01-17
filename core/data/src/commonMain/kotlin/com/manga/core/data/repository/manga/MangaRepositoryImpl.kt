package com.manga.core.data.repository.manga

import com.manga.core.common.Resource
import com.manga.core.common.map
import com.manga.core.common.mapExpected
import com.manga.core.model.manga_dex.common.MangaException
import com.manga.core.model.manga_dex.manga.MangaVolume
import com.manga.core.model.manga_dex.manga.request.MangaChaptersRequest
import com.manga.core.model.manga_dex.manga.request.MangaListRequest
import com.manga.core.model.manga_dex.manga.request.MangaRequest
import com.manga.core.network.datasource.MangaDexMangaNetworkDataSource
import com.manga.core.network.manga_dex.model.asExternalModel
import com.manga.core.network.manga_dex.model.common.asPageable
import com.manga.core.network.manga_dex.model.common.relationships.MangaRelationship
import com.manga.core.network.manga_dex.model.common.relationships.asExternalModel
import com.manga.core.network.manga_dex.model.manga.MangaChaptersNetworkModel
import com.manga.core.network.manga_dex.model.manga.asExternalModel
import com.manga.core.network.manga_dex.model.manga.asPageableManga
import org.koin.core.annotation.Single

@Single
internal class MangaRepositoryImpl(
    private val mangaDataSource: MangaDexMangaNetworkDataSource
) : MangaRepository {
    override suspend fun mangaList(request: MangaListRequest) =
        mangaDataSource.mangaList(request)
            .mapExpected { it.asExternalModel() }
            .map { it.asPageable(MangaRelationship::asExternalModel) }

    override suspend fun chapters(request: MangaChaptersRequest): Resource<List<MangaVolume>, MangaException> =
        mangaDataSource.chapters(request)
            .map {
                it.volumes
                    ?.values
                    ?.filterNotNull()
                    ?.map(MangaChaptersNetworkModel.Volume::asExternalModel)
                    ?: emptyList()
            }
            .mapExpected { it.asExternalModel() }

    override suspend fun manga(request: MangaRequest) =
        mangaDataSource.manga(request)
            .map { it.data?.asExternalModel() }
            .mapExpected { it.asExternalModel() }
}


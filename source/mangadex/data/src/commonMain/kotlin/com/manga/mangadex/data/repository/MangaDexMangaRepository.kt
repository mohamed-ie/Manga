package com.manga.mangadex.data.repository

import com.manga.core.common.Pageable
import com.manga.core.common.Resource
import com.manga.core.common.map
import com.manga.core.common.mapExpected
import com.manga.core.data.repository.manga.MangaRepository
import com.manga.core.model.common.MangaException
import com.manga.core.model.manga.Manga
import com.manga.core.model.request.Request
import com.manga.core.model.request.anyParam
import com.manga.core.model.request.listParam
import com.manga.mangadex.network.datasource.MangaDexMangaNetworkDataSource
import com.manga.mangadex.network.model.asExternalModel
import com.manga.mangadex.network.model.common.asPageable
import com.manga.mangadex.network.model.common.relationships.MangaRelationship
import com.manga.mangadex.network.model.common.relationships.asExternalModel
import org.koin.core.annotation.Single

@Single
class MangaDexMangaRepository(
    private val networkDataSource: MangaDexMangaNetworkDataSource
) : MangaRepository {
    override suspend fun manga(request: Request): Resource<Manga?, MangaException> =
        networkDataSource.manga(request)
            .map { it.data?.asExternalModel() }
            .mapExpected { it.asExternalModel() }

    override suspend fun mangaList(
        request: Request,
        nextKey: Int?
    ): Resource<Pageable<Manga, Int>, MangaException> {
        val updatedRequest = request +
                listParam("includes", listOf("cover_art")) +
                listParam("contentRating", listOf("safe")) +
                anyParam("offset", nextKey?:0)

        return networkDataSource.mangaList(updatedRequest)
            .mapExpected { it.asExternalModel() }
            .map { it.asPageable(MangaRelationship::asExternalModel) }
    }
}
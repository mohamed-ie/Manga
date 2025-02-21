package com.manga.mangadex.network.datasource

import com.manga.core.common.Resource
import com.manga.core.model.request.Request
import com.manga.mangadex.network.model.MangaDexErrorNetworkModel
import com.manga.mangadex.network.model.common.MangaDexPageable
import com.manga.mangadex.network.model.common.relationships.MangaRelationship
import com.manga.mangadex.network.model.manga.MangaChaptersNetworkModel
import com.manga.mangadex.network.model.manga.MangaNetworkModel

interface MangaDexMangaNetworkDataSource {
    suspend fun mangaList(request: Request): Resource<MangaDexPageable<MangaRelationship>, MangaDexErrorNetworkModel>
    suspend fun manga(request: Request): Resource<MangaNetworkModel, MangaDexErrorNetworkModel>
    suspend fun chapters(request: Request): Resource<MangaChaptersNetworkModel, MangaDexErrorNetworkModel>
}
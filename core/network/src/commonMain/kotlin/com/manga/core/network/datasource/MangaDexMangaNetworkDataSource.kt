package com.manga.core.network.datasource

import com.manga.core.common.Resource
import com.manga.core.network.manga_dex.model.MangaDexErrorNetworkModel
import com.manga.core.network.manga_dex.model.manga.MangaChaptersNetworkModel
import com.manga.core.network.manga_dex.model.manga.MangaListNetworkModel
import com.manga.core.network.manga_dex.model.manga.MangaNetworkModel
import com.manga.core.model.manga_dex.manga.request.MangaChaptersRequest
import com.manga.core.model.manga_dex.manga.request.MangaListRequest
import com.manga.core.model.manga_dex.manga.request.MangaRequest
import com.manga.core.network.manga_dex.model.common.MangaDexPageable
import com.manga.core.network.manga_dex.model.common.relationships.MangaRelationship

interface MangaDexMangaNetworkDataSource {
    suspend fun mangaList(request: MangaListRequest = MangaListRequest()): Resource<MangaDexPageable<MangaRelationship>, MangaDexErrorNetworkModel>
    suspend fun manga(request: MangaRequest): Resource<MangaNetworkModel, MangaDexErrorNetworkModel>
    suspend fun chapters(request: MangaChaptersRequest): Resource<MangaChaptersNetworkModel, MangaDexErrorNetworkModel>
}
package com.manga.core.network.datasource

import com.manga.core.common.Resource
import com.manga.core.network.response.MangaDexErrorResponse
import com.manga.core.network.response.manga.MangaChaptersResponse
import com.manga.core.network.response.manga.MangaListResponse
import com.manga.core.network.response.manga.MangaResponse
import com.manga.core.model.manga.request.MangaChaptersRequest
import com.manga.core.model.manga.request.MangaListRequest
import com.manga.core.model.manga.request.MangaRequest

interface MangaDexMangaNetworkDataSource {
    suspend fun mangaList(request: MangaListRequest = MangaListRequest()): Resource<MangaListResponse, MangaDexErrorResponse>
    suspend fun manga(request: MangaRequest): Resource<MangaResponse, MangaDexErrorResponse>
    suspend fun chapters(request: MangaChaptersRequest): Resource<MangaChaptersResponse, MangaDexErrorResponse>
}
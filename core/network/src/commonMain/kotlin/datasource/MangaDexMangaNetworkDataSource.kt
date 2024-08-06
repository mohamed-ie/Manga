package datasource

import core.common.Resource
import response.MangaDexErrorResponse
import response.manga.MangaChaptersResponse
import response.manga.MangaListResponse
import response.manga.MangaResponse
import manga.request.MangaChaptersRequest
import manga.request.MangaListRequest
import manga.request.MangaRequest

interface MangaDexMangaNetworkDataSource {
    suspend fun mangaList(request: MangaListRequest =MangaListRequest()): Resource<MangaListResponse, MangaDexErrorResponse>
    suspend fun manga(request: MangaRequest): Resource<MangaResponse, MangaDexErrorResponse>
    suspend fun chapters(request: MangaChaptersRequest): Resource<MangaChaptersResponse, MangaDexErrorResponse>
}
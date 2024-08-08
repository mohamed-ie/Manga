package manga

import androidx.paging.PagingData
import core.common.Resource
import kotlinx.coroutines.flow.Flow
import manga.request.MangaChaptersRequest
import manga.request.MangaListRequest
import manga.request.MangaRequest
import response.MangaDexErrorResponse

interface MangaRepository {
    fun mangaList(request: MangaListRequest = MangaListRequest()): Flow<PagingData<MinManga>>
    suspend fun manga(request: MangaRequest): Resource<Manga, MangaDexErrorResponse>
    suspend fun chapters(request: MangaChaptersRequest): Resource<List<MangaVolume>, MangaDexErrorResponse>
}


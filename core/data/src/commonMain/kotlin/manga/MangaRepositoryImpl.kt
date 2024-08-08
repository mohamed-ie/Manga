package manga

import MangaException
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import core.common.Resource
import core.common.map
import datasource.MangaDexMangaNetworkDataSource
import kotlinx.coroutines.flow.Flow
import manga.request.MangaChaptersRequest
import manga.request.MangaListRequest
import manga.request.MangaRequest
import org.koin.core.annotation.Single
import response.MangaDexErrorResponse
import response.manga.MangaChaptersResponse
import response.manga.asExternalModel
import response.manga.asManga

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

    override suspend fun chapters(request: MangaChaptersRequest): Resource<List<MangaVolume>, MangaDexErrorResponse> =
        mangaDataSource.chapters(request)
            .map {
                it.volumes
                    ?.values
                    ?.filterNotNull()
                    ?.map(MangaChaptersResponse.Volume::asExternalModel)
                    ?: emptyList()
            }

    override suspend fun manga(request: MangaRequest): Resource<Manga, MangaDexErrorResponse> =
        mangaDataSource.manga(request)
            .map { it.data?.asManga() ?: throw MangaException("Can not find manga with ${request.id} id") }
}


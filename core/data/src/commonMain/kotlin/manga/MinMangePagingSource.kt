package manga

import androidx.paging.PagingSource
import androidx.paging.PagingState
import core.common.getOrThrow
import core.common.map
import datasource.MangaDexMangaNetworkDataSource
import manga.request.MangaListRequest
import response.manga.asPageable

internal class MinMangePagingSource(
    private val mangaDataSource: MangaDexMangaNetworkDataSource,
    private val request: MangaListRequest,
    private val languageTag: String = "en"
) : PagingSource<Int, MinManga>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MinManga> {
        try {
            val offset = params.key ?: 0
            val response = mangaDataSource.mangaList(request.copy(offset = offset))
                .map { it.asPageable(languageTag) }
                .getOrThrow()

            return LoadResult.Page(
                data = response.data,
                prevKey = response.previousKey,
                nextKey = response.nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MinManga>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(request.limit) ?: anchorPage?.nextKey?.minus(request.limit)
        }
    }
}
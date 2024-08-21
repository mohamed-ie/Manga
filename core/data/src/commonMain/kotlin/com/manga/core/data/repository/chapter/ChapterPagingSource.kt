package com.manga.core.data.repository.chapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.manga.core.common.getOrThrow
import com.manga.core.common.map
import com.manga.core.model.chapter.Chapter
import com.manga.core.model.chapter.request.ChapterListRequest
import com.manga.core.network.datasource.MangaDexChapterNetworkDataSource
import com.manga.core.network.response.chapter.asPageable

internal class ChapterPagingSource(
    private val dataSource: MangaDexChapterNetworkDataSource,
    private val request: ChapterListRequest
) : PagingSource<Int, Chapter>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Chapter> {
        try {
            val offset = params.key ?: 0
            val response = dataSource.chapterList(request.copy(offset = offset))
                .map { it.asPageable() }
                .getOrThrow()

            return LoadResult.Page(
                data = response.data,
                prevKey = response.previousKey,
                nextKey = response.nextKey
            )
        } catch (e: Exception) {
            throw e
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Chapter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(request.limit) ?: anchorPage?.nextKey?.minus(request.limit)        }
    }

}
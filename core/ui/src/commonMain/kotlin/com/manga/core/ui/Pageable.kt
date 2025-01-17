package com.manga.core.ui

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.manga.core.common.Resource
import com.manga.core.common.onSuccess
import com.manga.core.model.manga_dex.common.MangaException
import com.manga.core.common.Pageable
import com.manga.core.common.onFailure

inline fun <T : Any> offsetPagerOf(
    limit: Int = 100,
    config: PagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 60),
    crossinline onFailure: (UiText) -> Unit = {},
    crossinline request: suspend (offset: Int?) -> Resource<Pageable<T, Int>, MangaException>
) = Pager(
    config = config,
    pagingSourceFactory = {
        object : PagingSource<Int, T>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
                val offset = params.key
                request(offset).onSuccess { pageable ->
                    return LoadResult.Page(
                        data = pageable.data,
                        prevKey = pageable.previousKey,
                        nextKey = pageable.nextKey
                    )
                }
                    .onFailure(onFailure)
                    .onFailure { e: Throwable -> return LoadResult.Error(e) }
                return LoadResult.Invalid()
            }

            override fun getRefreshKey(state: PagingState<Int, T>): Int? {
                return state.anchorPosition?.let { anchorPosition ->
                    val anchorPage = state.closestPageToPosition(anchorPosition)
                    anchorPage?.prevKey?.plus(limit) ?: anchorPage?.nextKey?.minus(limit)
                }
            }
        }
    }
)
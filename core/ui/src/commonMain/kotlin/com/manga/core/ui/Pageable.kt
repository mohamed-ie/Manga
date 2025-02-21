package com.manga.core.ui

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import com.manga.core.common.Pageable
import com.manga.core.common.Resource
import com.manga.core.common.onFailure
import com.manga.core.common.onSuccess
import com.manga.core.model.common.MangaException
import com.manga.core.ui.utils.onFailure
import kotlinx.coroutines.CoroutineScope

inline fun <T : Any> pagerOf(
    config: PagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 60),
    crossinline onFailure: (UiText) -> Unit = {},
    crossinline request: suspend (offset: Int?) -> Resource<Pageable<T, Int>, MangaException>
) = Pager(
    config = config,
    pagingSourceFactory = {
        object : PagingSource<Int, T>() {
            var step: Int? = null
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
                val offset = params.key
                request(offset).onSuccess { pageable ->
                    step = pageable.run { nextKey?.minus(previousKey ?: 0) }
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
                    step?.let { anchorPage?.prevKey?.plus(it) ?: anchorPage?.nextKey?.minus(it) }
                }
            }
        }
    }
)

inline fun <T : Any> pagerFlowOf(
    config: PagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 60),
    crossinline onFailure: (UiText) -> Unit = {},
    crossinline request: suspend (offset: Int?) -> Resource<Pageable<T, Int>, MangaException>
) = pagerOf(
    config = config,
    onFailure = onFailure,
    request = request
).flow

inline fun <T : Any> pagerFlowOf(
    scope: CoroutineScope,
    config: PagingConfig = PagingConfig(pageSize = 20, initialLoadSize = 60),
    crossinline onFailure: (UiText) -> Unit = {},
    crossinline request: suspend (offset: Int?) -> Resource<Pageable<T, Int>, MangaException>
) = pagerFlowOf(
    config = config,
    onFailure = onFailure,
    request = request
).cachedIn(scope)

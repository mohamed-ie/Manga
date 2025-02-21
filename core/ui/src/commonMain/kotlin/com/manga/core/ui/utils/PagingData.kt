package com.manga.core.ui.utils

import androidx.paging.PagingData
import androidx.paging.filter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline fun <T : Any, K> PagingData<T>.distinctBy(crossinline selector: (T) -> K): PagingData<T> {
    val duplicatedKeys = mutableSetOf<K>()

    return filter { item ->
        val key = selector(item)
        if (duplicatedKeys.contains(key)) return@filter false
        duplicatedKeys.add(key)
    }
}
inline fun <T : Any, K> Flow<PagingData<T>>.distinctBy(crossinline selector: (T) -> K): Flow<PagingData<T>> =
    map { it.distinctBy(selector) }
package com.manga.core.ui

import androidx.paging.PagingData
import androidx.paging.filter

fun <T : Any> PagingData<T>.distinct(): PagingData<T> {
    val duplicatedItems = mutableSetOf<T>()
    return filter { item ->
        if (duplicatedItems.contains(item)) return@filter false
        duplicatedItems.add(item)
    }
}

inline fun <T : Any, K> PagingData<T>.distinctBy(crossinline selector: (T) -> K): PagingData<T> {
    val duplicatedKeys = mutableSetOf<K>()

   return filter { item ->
        val key = selector(item)
        if (duplicatedKeys.contains(key)) return@filter false
        duplicatedKeys.add(key)
    }
}
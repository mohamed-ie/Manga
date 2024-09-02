package com.manga.core.common

data class Pageable<T, K>(
    val data: List<T>,
    val nextKey: K? = null,
    val previousKey: K? = null
)

typealias IntPageable<T> = Pageable<T, Int>

inline fun <I, O, K> Pageable<I, K>.map(transformer: (I) -> O) =
    Pageable(data = data.map(transformer), nextKey = nextKey, previousKey = previousKey)

fun <I, O, K> Pageable<I, K>.replaceData(newData: List<O>) =
    Pageable(data = newData, nextKey = nextKey, previousKey)

fun <D, K, E : Throwable> Resource.Companion.pageable(pageable: Pageable<D, K>) =
    success<Pageable<D, K>, E>(pageable)

fun <D, K, E : Throwable> Resource.Companion.pageable(
    data: List<D>,
    nextKey: K? = null,
    previousKey: K? = null
) =
    success<Pageable<D, K>, E>(
        Pageable(
            data = data,
            nextKey = nextKey,
            previousKey = previousKey,
        )
    )
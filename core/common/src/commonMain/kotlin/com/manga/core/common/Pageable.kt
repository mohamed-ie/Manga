package com.manga.core.common

typealias IntPageable<T> = Pageable<T, Int>
typealias PageableResource<Data, Key, Exception> = Resource<Pageable<Data, Key>, Exception>

data class Pageable<out T, out K>(
    val data: List<T>,
    val nextKey: K? = null,
    val previousKey: K? = null
) {
    companion object {
        fun <T, K> empty() = Pageable<T, K>(emptyList())
    }
}

inline fun <I, O, K> Pageable<I, K>.map(transformer: (I) -> O): Pageable<O, K> =
    Pageable(data = data.map(transformer), nextKey = nextKey, previousKey = previousKey)

inline fun <D, K, reified E : Throwable> Resource.Companion.pageable(
    data: List<D>,
    nextKey: K? = null,
    previousKey: K? = null
) =
    resourceOf<Pageable<D, K>, E> {
        Pageable(
            data = data,
            nextKey = nextKey,
            previousKey = previousKey,
        )
    }

inline fun <I, O, K, reified E : Throwable> Resource<Pageable<I, K>, E>.mapItems(transformerItem: (I) -> O): Resource<Pageable<O, K>, E> =
    map { it.map(transformerItem) }
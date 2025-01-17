package com.manga.core.network.manga_dex.model.common

import com.manga.core.common.Pageable
import kotlinx.serialization.Serializable

@Serializable
open class MangaDexPageable<T>(
    open val offset: Int? = null,
    open val total: Int? = null,
    open val limit: Int? = null,
) : MangaDexNetworkModel<List<T>>()


val MangaDexPageable<*>.nextKey
    get() = offset?.plus(limit ?: 0)
        ?.coerceIn(0, total)

val MangaDexPageable<*>.previousKey
    get() = offset?.minus(limit ?: 0)
        ?.coerceIn(0, total)

inline fun <reified I,reified O> MangaDexPageable<I>.asPageable(transformer: (I) -> O) =
    Pageable(
        data = data?.filterNotNull()?.mapNotNull(transformer) ?: emptyList(),
        nextKey = nextKey,
        previousKey = previousKey
    )

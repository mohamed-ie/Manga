package com.manga.mangadex.network.model.common

import com.manga.core.common.Pageable
import kotlinx.serialization.Serializable

@Serializable
open class MangaDexPageable<T>(
    open val offset: Int? = null,
    open val total: Int? = null,
    open val limit: Int? = null,
) : MangaDexNetworkModel<List<T>>()

val MangaDexPageable<*>.nextKey
    get() = total?.let { total ->
        offset?.plus(limit ?: 0)
            ?.coerceAtMost(total)
    }

val MangaDexPageable<*>.previousKey
    get() = offset?.takeIf { it > 0 }
        ?.minus(limit ?: 0)
        ?.coerceAtLeast(0)

inline fun <reified I, reified O> MangaDexPageable<I>.asPageable(transformer: (I) -> O) =
    Pageable(
        data = data?.filterNotNull()?.mapNotNull(transformer) ?: emptyList(),
        nextKey = nextKey,
        previousKey = previousKey
    )

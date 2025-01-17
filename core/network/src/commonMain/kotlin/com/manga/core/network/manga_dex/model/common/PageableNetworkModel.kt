package com.manga.core.network.manga_dex.model.common

import com.manga.core.common.Pageable
import kotlinx.serialization.Serializable

@Serializable
abstract class PageableNetworkModel(
    open val offset: Int = 0,
    open val total: Int = 0,
    open val limit: Int = 0
)

val PageableNetworkModel.nextKey
    get() = offset.plus(limit)
        .coerceIn(0, total)
        .let { if (offset == total) null else it }

val PageableNetworkModel.previousKey
    get() = offset.minus(limit)
        .coerceIn(0, total)
        .let { if (offset == 0) null else it }

inline fun <reified T : PageableNetworkModel, O> T.asPageable(transformer: (T) -> List<O>) =
    Pageable(
        data = transformer(this),
        nextKey = nextKey,
        previousKey = previousKey
    )

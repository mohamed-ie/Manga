package com.manga.core.network.response.common

import kotlinx.serialization.Serializable

@Serializable
abstract class PageableResponse(
    open val offset: Int = 0,
    open val total: Int = 0,
    open val limit: Int = 0
)

val PageableResponse.nextKey
    get() = offset.plus(limit)
        .coerceIn(0, total)
        .let { if (offset == total) null else it }

val PageableResponse.previousKey
    get() = offset.minus(limit)
        .coerceIn(0, total)
        .let { if (offset == 0) null else it }
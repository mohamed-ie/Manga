package com.manga.core.network.response.manga

import com.manga.core.network.response.common.PageableResponse
import com.manga.core.network.response.common.nextKey
import com.manga.core.network.response.common.previousKey
import core.common.com.manga.core.common.Pageable
import kotlinx.serialization.Serializable

@Serializable
data class MangaListResponse(
    val result: String? = null,
    val data: List<MangaDto?>? = null,
    val response: String? = null,
) : PageableResponse()

fun MangaListResponse.asPageableManga() = Pageable(
    data = data
        ?.filterNotNull()
        ?.mapNotNull(MangaDto::asMangaDexModel)
        ?: emptyList(),
    nextKey = nextKey,
    previousKey = previousKey,
    totalCount = total ?: 0
)
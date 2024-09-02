package com.manga.core.network.response.chapter

import com.manga.core.network.response.common.PageableResponse
import com.manga.core.network.response.common.nextKey
import com.manga.core.network.response.common.previousKey
import com.manga.core.common.Pageable
import kotlinx.serialization.Serializable

@Serializable
data class ChapterListResponse(
    val result: String? = null,
    val data: List<ChapterDto> = emptyList(),
    val response: String? = null
):PageableResponse()

fun ChapterListResponse.asPageable() = Pageable(
    data = data.mapNotNull(ChapterDto::asExternalModel),
    nextKey = nextKey,
    previousKey = previousKey
)
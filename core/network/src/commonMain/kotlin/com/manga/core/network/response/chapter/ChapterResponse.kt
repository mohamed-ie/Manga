package com.manga.core.network.response.chapter

import com.manga.core.model.chapter.MangaDexChapter
import kotlinx.serialization.Serializable

@Serializable
data class ChapterResponse(
    val result: String? = null,
    val data: Map<String, ChapterDto>? = null,
    val response: String? = null,
)

fun ChapterResponse.asExternalModelList(): List<MangaDexChapter> = data?.mapNotNull { it.value.asExternalModel() } ?: emptyList()


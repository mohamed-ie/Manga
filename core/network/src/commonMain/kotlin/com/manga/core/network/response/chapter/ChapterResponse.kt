package com.manga.core.network.response.chapter

import com.manga.core.common.ext.filterValuesNotNull
import com.manga.core.model.chapter.Chapter
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ChapterResponse(
    val result: String? = null,
    val data: Map<String, ChapterDto>? = null,
    val response: String? = null,
)

fun ChapterResponse.asExternalModel(): Chapter? = data?.filterValuesNotNull()?.run {
    val id = keys.firstOrNull() ?: return null
    val attributes = this[id]?.attributes
    return Chapter(
        id = id,
        name = attributes?.chapter ?: return null,
        publishAt =attributes.publishAt?.let(Instant::parse) ?: return null,
    )
}


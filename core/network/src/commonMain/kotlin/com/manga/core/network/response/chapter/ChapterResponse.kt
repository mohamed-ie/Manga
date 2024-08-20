package com.manga.core.network.response.chapter

import com.manga.core.model.chapter.Chapter
import com.manga.core.common.ext.filterValuesNotNull
import kotlinx.serialization.Serializable

@Serializable
data class ChapterResponse(
    val result: String? = null,
    val data: Map<String, Chapter>? = null,
    val response: String? = null
) {
    @Serializable
    data class Chapter(
        val id: String? = null,
        val type: String? = null,
        val attributes: Attributes? = null,
    )

    @Serializable
    data class Attributes(
        val volume: String? = null,
        val chapter: String? = null,
        val title: String? = null,
        val translatedLanguage: String? = null,
        val externalUrl: String? = null,
        val publishAt: String? = null,
        val readableAt: String? = null,
        val createdAt: String? = null,
        val updatedAt: String? = null,
        val pages: Int? = null,
        val version: Int? = null,
    )
}

fun ChapterResponse.asExternalModel(): Chapter? = data?.filterValuesNotNull()?.run {
    val id = keys.firstOrNull() ?: return null
    return Chapter(
        id = id,
        name = this[id]?.attributes?.chapter ?: return null
    )
}

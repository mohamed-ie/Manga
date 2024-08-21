package com.manga.core.network.response.chapter

import com.manga.core.model.chapter.Chapter
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ChapterDto(
    val id: String? = null,
    val type: String? = null,
    val attributes: Attributes? = null,
    val relationships: List<Map<String, String>?> = emptyList(),
) {

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

fun ChapterDto.asExternalModel(): Chapter? {
    return Chapter(
        id = id ?: return null,
        name = attributes?.chapter ?: "",
        publishAt = attributes?.publishAt?.let(Instant::parse) ?: return null
    )
}
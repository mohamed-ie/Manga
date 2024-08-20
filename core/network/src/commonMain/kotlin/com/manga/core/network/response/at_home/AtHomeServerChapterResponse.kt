package com.manga.core.network.response.at_home

import com.manga.core.model.at_home.AtHomeChapter
import kotlinx.serialization.Serializable

@Serializable
data class AtHomeServerChapterResponse(
    val result: String? = null,
    val baseUrl: String? = null,
    val chapter: Chapter? = null
) {
    @Serializable
    data class Chapter(
        val hash: String? = null,
        val data: List<String?>? = null,
        val dataSaver: List<String?>? = null,
    )
}

fun AtHomeServerChapterResponse.asExternalModel() : AtHomeChapter? =
    baseUrl?.let { baseUrl ->
        val hash = chapter?.hash ?: return@let null

        val images = chapter.data
            ?.filterNotNull()
            ?.map { "$baseUrl/data/$hash/$it" }

        val dataSaverImages = chapter.dataSaver
            ?.filterNotNull()
            ?.map { "$baseUrl/dataSaver/$hash/$it" }

        AtHomeChapter(
            images = images ?: emptyList(),
            dataSaverImages = dataSaverImages ?: emptyList()
        )
    }
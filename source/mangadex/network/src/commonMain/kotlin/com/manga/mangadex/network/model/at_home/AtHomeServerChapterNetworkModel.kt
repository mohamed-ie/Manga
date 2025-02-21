package com.manga.mangadex.network.model.at_home

import kotlinx.serialization.Serializable

@Serializable
data class AtHomeServerChapterNetworkModel(
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
//
//fun AtHomeServerChapterNetworkModel.asExternalModel() : AtHomeChapter? =
//    baseUrl?.let { baseUrl ->
//        val hash = chapter?.hash ?: return@let null
//
//        val images = chapter.data
//            ?.filterNotNull()
//            ?.map { "$baseUrl/data/$hash/$it" }
//
//        val dataSaverImages = chapter.dataSaver
//            ?.filterNotNull()
//            ?.map { "$baseUrl/dataSaver/$hash/$it" }
//
//        AtHomeChapter(
//            images = images ?: emptyList(),
//            dataSaverImages = dataSaverImages ?: emptyList()
//        )
//    }
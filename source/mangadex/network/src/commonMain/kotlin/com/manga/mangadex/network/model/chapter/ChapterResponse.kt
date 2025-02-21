package com.manga.mangadex.network.model.chapter

import com.manga.mangadex.network.model.common.relationships.ChapterRelationship
import kotlinx.serialization.Serializable

@Serializable
data class ChapterNetworkModel(
    val result: String? = null,
    val data: List<ChapterRelationship?>? = null,
    val response: String? = null,
)
//
//fun ChapterNetworkModel.asExternalModelList(): List<MangaDexChapter> = data?.filterNotNull()
//    ?.mapNotNull(ChapterRelationship::asExternalModel)
//    ?: emptyList()


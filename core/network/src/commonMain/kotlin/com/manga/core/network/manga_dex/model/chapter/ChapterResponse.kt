package com.manga.core.network.manga_dex.model.chapter

import com.manga.core.model.manga_dex.chapter.MangaDexChapter
import com.manga.core.network.manga_dex.model.common.relationships.ChapterRelationship
import com.manga.core.network.manga_dex.model.common.relationships.asExternalModel
import kotlinx.serialization.Serializable

@Serializable
data class ChapterNetworkModel(
    val result: String? = null,
    val data: List<ChapterRelationship?>? = null,
    val response: String? = null,
)

fun ChapterNetworkModel.asExternalModelList(): List<MangaDexChapter> = data?.filterNotNull()
    ?.mapNotNull(ChapterRelationship::asExternalModel)
    ?: emptyList()


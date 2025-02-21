package com.manga.mangadex.network.model.chapter

import com.manga.mangadex.network.model.common.PageableNetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class ChapterListNetworkModel(
    val result: String? = null,
    val data: List<ChapterNetworkModel?>? = emptyList(),
    val response: String? = null
): PageableNetworkModel()

//fun ChapterListNetworkModel.asPageable() = Pageable(
//    data = data?.filterNotNull()?.map(ChapterNetworkModel::asExternalModelList)?.flatten() ?: emptyList(),
//    nextKey = nextKey,
//    previousKey = previousKey
//)
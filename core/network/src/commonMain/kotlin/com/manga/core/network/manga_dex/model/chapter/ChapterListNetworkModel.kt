package com.manga.core.network.manga_dex.model.chapter

import com.manga.core.network.manga_dex.model.common.PageableNetworkModel
import com.manga.core.network.manga_dex.model.common.nextKey
import com.manga.core.network.manga_dex.model.common.previousKey
import com.manga.core.common.Pageable
import kotlinx.serialization.Serializable

@Serializable
data class ChapterListNetworkModel(
    val result: String? = null,
    val data: List<ChapterNetworkModel?>? = emptyList(),
    val response: String? = null
): PageableNetworkModel()

fun ChapterListNetworkModel.asPageable() = Pageable(
    data = data?.filterNotNull()?.map(ChapterNetworkModel::asExternalModelList)?.flatten() ?: emptyList(),
    nextKey = nextKey,
    previousKey = previousKey
)
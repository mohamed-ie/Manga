package com.manga.mangadex.network.model.manga

import com.manga.core.common.Pageable
import com.manga.mangadex.network.model.common.PageableNetworkModel
import com.manga.mangadex.network.model.common.nextKey
import com.manga.mangadex.network.model.common.previousKey
import com.manga.mangadex.network.model.common.relationships.MangaRelationship
import com.manga.mangadex.network.model.common.relationships.asExternalModel
import kotlinx.serialization.Serializable

@Serializable
data class MangaListNetworkModel(
    val result: String? = null,
    val data: List<MangaRelationship?>? = null,
    val response: String? = null,
) : PageableNetworkModel()

fun MangaListNetworkModel.asPageableManga() = Pageable(
    data = data
        ?.filterNotNull()
        ?.mapNotNull(MangaRelationship::asExternalModel)
        ?: emptyList(),
    nextKey = nextKey,
    previousKey = previousKey
)
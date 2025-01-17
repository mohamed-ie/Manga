package com.manga.core.network.manga_dex.model.manga

import com.manga.core.network.manga_dex.model.common.PageableNetworkModel
import com.manga.core.network.manga_dex.model.common.nextKey
import com.manga.core.network.manga_dex.model.common.previousKey
import com.manga.core.common.Pageable
import com.manga.core.network.manga_dex.model.common.relationships.MangaRelationship
import com.manga.core.network.manga_dex.model.common.relationships.asExternalModel
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
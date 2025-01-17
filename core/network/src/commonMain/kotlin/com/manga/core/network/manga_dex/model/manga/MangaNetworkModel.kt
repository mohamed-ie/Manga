package com.manga.core.network.manga_dex.model.manga

import com.manga.core.network.manga_dex.model.common.relationships.MangaRelationship
import kotlinx.serialization.Serializable

@Serializable
data class MangaNetworkModel(
    val result: String? = null,
    val response: String? = null,
    val data: MangaRelationship? = null
)
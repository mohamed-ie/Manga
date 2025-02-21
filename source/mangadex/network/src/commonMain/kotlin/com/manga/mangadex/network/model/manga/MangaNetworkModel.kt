package com.manga.mangadex.network.model.manga

import com.manga.mangadex.network.model.common.relationships.MangaRelationship
import com.manga.mangadex.network.model.common.relationships.RelationshipNetworkModel
import kotlinx.serialization.Serializable

@Serializable
data class MangaNetworkModel(
    val result: String? = null,
    val response: String? = null,
    val data: MangaRelationship? = null,
    val relationships: List<RelationshipNetworkModel>? = null,
)
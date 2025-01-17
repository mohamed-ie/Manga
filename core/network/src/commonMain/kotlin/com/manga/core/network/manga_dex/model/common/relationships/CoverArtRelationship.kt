package com.manga.core.network.manga_dex.model.common.relationships

import com.manga.core.model.manga_dex.common.MangaDexCover
import com.manga.core.network.manga_dex.asInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("cover_art")
data class CoverArtRelationship(val attributes: Attributes? = null) : RelationshipNetworkModel() {
    @Serializable
    data class Attributes(
        val volume: String? = null,
        @SerialName("fileName")
        val filename: String? = null,
        val description: String? = null,
        val locale: String? = null,
        val version: Int? = null,
        val createdAt: String? = null,
        val updatedAt: String? = null
    )
}

fun CoverArtRelationship.asMangaDexCover(id: String? = null): MangaDexCover? {
    return attributes?.asMangaDexCover(id)
}

fun CoverArtRelationship.Attributes.asMangaDexCover(id: String? = null): MangaDexCover? {
    return MangaDexCover(
        id = id ?: return null,
        volume = volume,
        filename = filename ?: return null,
        description = description,
        locale = locale,
        version = version ?: return null,
        createdAt = createdAt?.asInstant() ?: return null,
        updatedAt = updatedAt?.asInstant() ?: return null
    )
}
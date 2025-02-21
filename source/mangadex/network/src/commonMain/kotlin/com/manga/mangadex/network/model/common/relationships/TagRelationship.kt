package com.manga.mangadex.network.model.common.relationships

import com.manga.mangadex.network.model.common.MangaDexTag
import com.manga.mangadex.network.model.common.MangaDexTagGroup
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("tag")
data class TagRelationship(val attributes: Attributes?) : RelationshipNetworkModel() {
    @Serializable
    data class Attributes(
        val name: Map<String?, String?>? = null,
        val description: Map<String?, String?>? = null,
        val group: String? = null,
        val version: Int? = null
    )
}

fun TagRelationship.asExternalModel(): MangaDexTag? =
    attributes?.run {
        MangaDexTag(
            id = id ?: return null,
            name = name?.asMangaDexLocalizedString ?: return null,
            description = description?.asMangaDexLocalizedString ,
            group = group?.uppercase()?.let(MangaDexTagGroup::valueOf) ?: return null,
            version = version ?: return null
        )
    }
package com.manga.core.network.response.common

import com.manga.core.model.common.MangaDexTag
import com.manga.core.model.manga.MangaDexTagGroup
import com.manga.core.network.response.common.NoAttributeRelationship.id
import kotlinx.serialization.Serializable

@Serializable
data class TagRelationship(val attributes: Attributes?):RelationshipDto(){
    @Serializable
    data class Attributes(
        val name: Map<String?, String?>? = null,
        val description: Map<String?, String?>? = null,
        val group: String? = null,
        val version: Int? = null
    )
}

fun TagRelationship.asMangaDexModel(): MangaDexTag? =
    attributes?.asMangaDexModel()

fun TagRelationship.Attributes.asMangaDexModel(): MangaDexTag? {
    return MangaDexTag(
        id = id ?: return null,
        name = name?.asMangaDexLocalizedString ?: return null,
        description = description?.asMangaDexLocalizedString ?: return null,
        group = group?.uppercase()?.let(MangaDexTagGroup::valueOf) ?: return null,
        version = version ?: return null
    )
}
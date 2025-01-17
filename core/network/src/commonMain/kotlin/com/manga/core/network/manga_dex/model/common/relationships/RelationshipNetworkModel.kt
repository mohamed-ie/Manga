package com.manga.core.network.manga_dex.model.common.relationships

import com.manga.core.model.manga_dex.common.MangaDexLink
import com.manga.core.model.manga_dex.common.MangaDexLocalizedString
import kotlinx.serialization.Serializable

@Serializable
sealed class RelationshipNetworkModel(
    open val id: String? = null,
)

@Serializable
data object NoAttributeRelationship : RelationshipNetworkModel()

internal val Map.Entry<String?, String?>.asMangaDexLink: MangaDexLink?
    get() {
        return MangaDexLink(
            locale = key ?: return null,
            url = value ?: return null,
        )
    }

internal val Map<String?, String?>.asMangaDexLocalizedString: MangaDexLocalizedString?
    get() {
        return firstNotNullOfOrNull {(key,value) ->
            MangaDexLocalizedString(
                locale = key ?: return null,
                value = value ?: return null,
            )
        }
    }
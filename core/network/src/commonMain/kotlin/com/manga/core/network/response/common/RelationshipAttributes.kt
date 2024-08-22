package com.manga.core.network.response.common

import com.manga.core.model.common.MangaDexLink
import com.manga.core.model.common.MangaDexLocalizedString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class RelationshipDto(
    open val id: String? = null,
)

@Serializable
data object NoAttributeRelationship : RelationshipDto()


internal val Map.Entry<String?, String?>.asMangaDexLink: MangaDexLink?
    get() {
        return MangaDexLink(
            locale = key ?: return null,
            url = value ?: return null,
        )
    }

internal val Map.Entry<String?, String?>.asMangaDexLocalizedString: MangaDexLocalizedString?
    get() {
        return MangaDexLocalizedString(
            locale = key ?: return null,
            value = value ?: return null,
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
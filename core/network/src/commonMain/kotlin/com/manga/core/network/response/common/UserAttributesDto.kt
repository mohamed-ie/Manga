package com.manga.core.network.response.common

import com.manga.core.model.common.MangaDexUser
import com.manga.core.network.response.common.NoAttributeRelationship.id
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("user")
data class UserRelationship(val attributes: Attributes? = null) : RelationshipDto() {
    @Serializable
    data class Attributes(
        val username: String? = null,
        val roles: List<String?>? = null,
        val version: Int? = null
    )
}

fun UserRelationship.asMangaDexModel(): MangaDexUser? {
    return attributes?.run {
        MangaDexUser(
            id = id ?: return null,
            username = username ?: "",
            roles = roles?.filterNotNull() ?: emptyList(),
            version = version
        )
    }
}

fun UserRelationship.Attributes.asMangaDexModel(): MangaDexUser? {
    return MangaDexUser(
        id = id ?: return null,
        username = username ?: "",
        roles = roles?.filterNotNull() ?: emptyList(),
        version = version
    )
}
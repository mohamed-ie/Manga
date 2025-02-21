package com.manga.mangadex.network.model.common.relationships

import com.manga.mangadex.network.model.common.MangaDexUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("user")
data class UserRelationship(val attributes: Attributes? = null) : RelationshipNetworkModel() {
    @Serializable
    data class Attributes(
        val username: String? = null,
        val roles: List<String?>? = null,
        val version: Int? = null
    )
}

fun com.manga.mangadex.network.model.common.relationships.UserRelationship.asExternalModel(): MangaDexUser? {
    return attributes?.run {
        MangaDexUser(
            id = id ?: return null,
            username = username ?: "",
            roles = roles?.filterNotNull() ?: emptyList(),
            version = version
        )
    }
}
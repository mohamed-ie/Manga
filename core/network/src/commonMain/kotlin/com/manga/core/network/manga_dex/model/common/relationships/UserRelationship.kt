package com.manga.core.network.manga_dex.model.common.relationships

import com.manga.core.model.manga_dex.common.MangaDexUser
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

fun UserRelationship.asExternalModel(): MangaDexUser? {
    return attributes?.run {
        MangaDexUser(
            id = id ?: return null,
            username = username ?: "",
            roles = roles?.filterNotNull() ?: emptyList(),
            version = version
        )
    }
}
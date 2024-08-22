package com.manga.core.network.response.common

import com.manga.core.model.common.MangaDexScanlationGroup
import com.manga.core.network.asInstant
import com.manga.core.network.response.common.NoAttributeRelationship.id
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("Scanlation_group")
data class ScanlationGroupRelationship(
    val attributes: Attributes? = null
) : RelationshipDto() {
    @Serializable
    data class Attributes(
        val name: String? = null,
        val altNames: List<String?>? = null,
        val locked: Boolean? = null,
        val website: String? = null,
        val ircServer: String? = null,
        val ircChannel: String? = null,
        val discord: String? = null,
        val contactEmail: String? = null,
        val description: String? = null,
        val twitter: String? = null,
        val mangaUpdates: String? = null,
        val focusedLanguages: List<String?>? = null,
        val official: Boolean? = null,
        val verified: Boolean? = null,
        val inactive: Boolean? = null,
        val publishDelay: String? = null,
        val exLicensed: Boolean? = null,
        val createdAt: String? = null,
        val updatedAt: String? = null,
        val version: Int? = null
    )
}

fun ScanlationGroupRelationship.asMangaDexModel(): MangaDexScanlationGroup? =
    attributes?.asMangaDexModel()

fun ScanlationGroupRelationship.Attributes.asMangaDexModel(): MangaDexScanlationGroup? {
    return MangaDexScanlationGroup(
        id = id ?: return null,
        name = name ?: "",
        altNames = altNames?.filterNotNull() ?: emptyList(),
        locked = locked,
        website = website,
        ircServer = ircServer,
        ircChannel = ircChannel,
        discord = discord,
        contactEmail = contactEmail,
        description = description,
        twitter = twitter,
        mangaUpdates = mangaUpdates,
        focusedLanguages = focusedLanguages?.filterNotNull() ?: emptyList(),
        official = official,
        verified = verified,
        inactive = inactive,
        publishDelay = publishDelay,
        exLicensed = exLicensed,
        createdAt = createdAt?.asInstant(),
        updatedAt = updatedAt?.asInstant(),
        version = version
    )
}

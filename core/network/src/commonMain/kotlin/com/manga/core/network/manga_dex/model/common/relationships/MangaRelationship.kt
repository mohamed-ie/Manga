package com.manga.core.network.manga_dex.model.common.relationships

import com.manga.core.model.manga_dex.common.MangaDexCover
import com.manga.core.model.manga_dex.common.MangaDexManga
import com.manga.core.model.manga_dex.common.MangaDexState
import com.manga.core.model.manga_dex.manga.MangaDexContentRating
import com.manga.core.model.manga_dex.manga.MangaDexPublicationDemographic
import com.manga.core.model.manga_dex.manga.MangaDexStatus
import com.manga.core.network.manga_dex.asInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("manga")
open class MangaRelationship(val attributes: Attributes? = null) : RelationshipNetworkModel() {
    @Serializable
    data class Attributes(
        val title: Map<String?, String>? = null,
        val altTitles: List<Map<String?, String>?>? = null,
        val description: Map<String?, String>? = null,
        val isLocked: Boolean? = null,
        val links: Map<String, String?>? = null,
        val originalLanguage: String? = null,
        val lastVolume: String? = null,
        val lastChapter: String? = null,
        val publicationDemographic: String? = null,
        val status: String? = null,
        val year: Int? = null,
        val contentRating: String? = null,
        val tags: List<TagRelationship?>? = null,
        val state: String? = null,
        val chapterNumbersResetOnNewVolume: Boolean? = null,
        val createdAt: String? = null,
        val updatedAt: String? = null,
        val version: Int? = null,
        val availableTranslatedLanguages: List<String?>? = null,
        val latestUploadedChapter: String? = null
    )
}

fun MangaRelationship.asExternalModel(cover: MangaDexCover? = null): MangaDexManga? =
    attributes?.run {
        MangaDexManga(
            id = id ?: return null,
            title = title?.asMangaDexLocalizedString ?: return null,
            altTitles = altTitles?.mapNotNull { it?.asMangaDexLocalizedString } ?: emptyList(),
            description = description?.asMangaDexLocalizedString ?: return null,
            isLocked = isLocked ?: return null,
            links = links?.mapNotNull { it.asMangaDexLink } ?: emptyList(),
            originalLanguage = originalLanguage ?: return null,
            lastVolume = lastVolume,
            lastChapter = lastChapter,
            publicationDemographic = publicationDemographic?.uppercase()?.let(
                MangaDexPublicationDemographic::valueOf
            ),
            status = status?.uppercase()?.let(MangaDexStatus::valueOf) ?: return null,
            year = year,
            contentRating = contentRating?.uppercase()?.let(MangaDexContentRating::valueOf)
                ?: return null,
            tags = tags?.filterNotNull()?.mapNotNull(TagRelationship::asExternalModel) ?: emptyList(),
            state = state?.uppercase()?.let(MangaDexState::valueOf) ?: return null,
            chapterNumbersResetOnNewVolume = chapterNumbersResetOnNewVolume ?: return null,
            createdAt = createdAt?.asInstant() ?: return null,
            updatedAt = updatedAt?.asInstant() ?: return null,
            version = version ?: return null,
            availableTranslatedLanguages = availableTranslatedLanguages?.filterNotNull() ?: emptyList(),
            latestUploadedChapter = latestUploadedChapter ?: return null,
            cover = cover
        )
    }

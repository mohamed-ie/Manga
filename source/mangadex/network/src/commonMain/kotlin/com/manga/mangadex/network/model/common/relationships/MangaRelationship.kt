package com.manga.mangadex.network.model.common.relationships

import com.manga.core.model.manga.Manga
import com.manga.mangadex.network.model.common.cover256
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("manga")
open class MangaRelationship(
    val attributes: Attributes? = null,
    val relationships: List<RelationshipNetworkModel>? = null,
) : RelationshipNetworkModel() {
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
//
//fun MangaRelationship.asExternalModel(cover: MangaDexCover? = null): MangaDexManga? =
//    attributes?.run {
//        MangaDexManga(
//            id = id ?: return null,
//            title = title?.asMangaDexLocalizedString ?: return null,
//            altTitles = altTitles?.mapNotNull { it?.asMangaDexLocalizedString } ?: emptyList(),
//            description = description?.asMangaDexLocalizedString ?: return null,
//            isLocked = isLocked ?: return null,
//            links = links?.mapNotNull { it.asMangaDexLink } ?: emptyList(),
//            originalLanguage = originalLanguage ?: return null,
//            lastVolume = lastVolume,
//            lastChapter = lastChapter,
//            publicationDemographic = publicationDemographic?.uppercase()?.let(
//                MangaDexPublicationDemographic::valueOf
//            ),
//            status = status?.uppercase()?.let(MangaDexStatus::valueOf) ?: return null,
//            year = year,
//            contentRating = contentRating?.uppercase()?.let(MangaDexContentRating::valueOf)
//                ?: return null,
//            tags = tags?.filterNotNull()?.mapNotNull(TagRelationship::asExternalModel) ?: emptyList(),
//            state = state?.uppercase()?.let(MangaDexState::valueOf) ?: return null,
//            chapterNumbersResetOnNewVolume = chapterNumbersResetOnNewVolume ?: return null,
//            createdAt = createdAt?.asInstant() ?: return null,
//            updatedAt = updatedAt?.asInstant() ?: return null,
//            version = version ?: return null,
//            availableTranslatedLanguages = availableTranslatedLanguages?.filterNotNull() ?: emptyList(),
//            latestUploadedChapter = latestUploadedChapter ?: return null,
//            cover = cover
//        )
//    }


fun MangaRelationship.asExternalModel(
    statistics: Manga.Statistics? = null
): Manga? = attributes?.run {
    Manga(
        id = id ?: return null,
        title = title?.asMangaDexLocalizedString?.value ?: return null,
        description = description?.asMangaDexLocalizedString?.value ?: return null,
        lastChapter = null,
        demographic = publicationDemographic?.uppercase()?.let(Manga.Demographic::valueOf),
        status = status?.uppercase()?.let(Manga.Status::valueOf) ?: return null,
        tags = tags?.filterNotNull()
            ?.mapNotNull { it.attributes?.name?.values?.firstNotNullOf { it } }
            ?.toImmutableList(),
        cover = (relationships?.first { it is CoverArtRelationship } as? CoverArtRelationship)
            ?.let { cover -> id?.let { cover.attributes?.asMangaDexCover(cover.id)?.cover256(it) } },
        statistics = statistics
    )
}
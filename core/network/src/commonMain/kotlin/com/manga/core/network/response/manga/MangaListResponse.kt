package com.manga.core.network.response.manga

import com.manga.core.common.ext.filterValuesNotNull
import com.manga.core.model.chapter.Chapter
import com.manga.core.model.manga.Manga
import com.manga.core.model.manga.MangaDexContentRating
import com.manga.core.model.manga.MangaDexPublicationDemographic
import com.manga.core.model.manga.MangaDexStatus
import com.manga.core.model.manga.MangaDexTagGroup
import com.manga.core.model.manga.MinManga
import com.manga.core.network.response.common.PageableResponse
import com.manga.core.network.response.common.nextKey
import com.manga.core.network.response.common.previousKey
import core.common.com.manga.core.common.Pageable
import kotlinx.serialization.Serializable

@Serializable
data class MangaListResponse(
    val result: String? = null,
    val data: List<Manga?>? = null,
    val response: String? = null,
) : PageableResponse() {

    @Serializable
    data class Manga(
        val relationships: List<Relationship?>? = null,
        val attributes: Attributes? = null,
        val id: String? = null,
        val type: String? = null
    )

    @Serializable
    data class TagAttributes(
        val name: Map<String, String?>? = null,
        val description: Map<String, String?>? = null,
        val group: String? = null,
        val version: Int? = null
    )

    @Serializable
    data class TagsItem(
        val relationships: List<Map<String, String?>?>? = null,
        val attributes: TagAttributes? = null,
        val id: String? = null,
        val type: String? = null
    )

    @Serializable
    data class Attributes(
        val lastVolume: String? = null,
        val latestUploadedChapter: String? = null,
        val year: Int? = null,
        val description: Map<String, String?>? = null,
        val title: Map<String, String?>? = null,
        val originalLanguage: String? = null,
        val lastChapter: String? = null,
        val version: Int? = null,
        val tags: List<TagsItem?>? = null,
        val altTitles: List<Map<String, String?>?>? = null,
        val publicationDemographic: String? = null,
        val createdAt: String? = null,
        val isLocked: Boolean? = null,
        val availableTranslatedLanguages: List<String?>? = null,
        val chapterNumbersResetOnNewVolume: Boolean? = null,
        val links: Map<String, String?>? = null,
        val contentRating: String? = null,
        val state: String? = null,
        val status: String? = null,
        val updatedAt: String? = null
    )

    @Serializable
    data class Relationship(
        val id: String? = null,
        val type: String? = null,
        val related: String? = null,
        val attributes: Map<String, String?>? = null
    )
}

fun MangaListResponse.asPageableManga() = Pageable(
    data = data
        ?.filterNotNull()
        ?.mapNotNull { it.asManga() }
        ?: emptyList(),
    nextKey = nextKey,
    previousKey = previousKey,
    totalCount = total ?: 0
)

private fun MangaListResponse.Manga.asMinManga(languageTag: String, lastChapter: Chapter?): MinManga? {
    return MinManga(
        id = id ?: return null,
        title = attributes?.title
            ?.run { this[languageTag] ?: values.firstOrNull { it != null } }
            ?: "",
        cover = relationships?.filterNotNull()?.cover256(mangaId = id),
        lastChapter = lastChapter,
        status = attributes?.status?.uppercase()?.let(MangaDexStatus::valueOf)
            ?: MangaDexStatus.ONGOING,
        publicationDemographic = attributes?.publicationDemographic?.uppercase()
            ?.let(MangaDexPublicationDemographic::valueOf)
    )
}

fun MangaListResponse.Manga.asManga(): Manga? {
    return Manga(
        id = id ?: return null,

        cover = relationships?.filterNotNull()?.cover(mangaId = id),

        titles = attributes?.run { altTitles?.plus(title) ?: listOf(title) }
            ?.filterNotNull()
            ?.flatMap { it.entries }
            ?.associate { it.toPair() }
            ?.filterValuesNotNull()
            ?: attributes?.title
                ?.filterValuesNotNull()
            ?: emptyMap(),

        descriptions = attributes?.description
            ?.filterValuesNotNull()
            ?: emptyMap(),

        tags = attributes?.tagGroups() ?: emptyMap(),

        availableTranslatedLanguages = attributes?.availableTranslatedLanguages
            ?.filterNotNull()
            ?: emptyList(),

        status = attributes?.status
            ?.uppercase()
            ?.let(MangaDexStatus::valueOf),

        contentRating = attributes?.contentRating
            ?.uppercase()
            ?.let(MangaDexContentRating::valueOf),

        publicationDemographic = attributes?.publicationDemographic?.uppercase()
            ?.let(MangaDexPublicationDemographic::valueOf) ?: MangaDexPublicationDemographic.NONE,

        latestUploadedChapterId = attributes?.latestUploadedChapter
    )
}

fun MangaListResponse.Attributes.tagGroups() = tags?.filterNotNull()
    ?.groupBy { it.attributes?.group }
    ?.mapNotNull { (group, items) ->
        group ?: return@mapNotNull null
        val tagGroup = group.uppercase().let(MangaDexTagGroup::valueOf)

        val tags = items.mapNotNull innerMapNotNull@{
            Manga.Tag(
                id = it.id ?: return@innerMapNotNull null,
                name = it.attributes?.name?.filterValuesNotNull() ?: return@innerMapNotNull null
            )
        }
        tagGroup to tags
    }?.toMap()

private fun List<MangaListResponse.Relationship>.cover(mangaId: String) =
    find { it.type == "cover_art" }
        ?.attributes
        ?.get("fileName")
        ?.let { "https://uploads.mangadex.org/covers/$mangaId/$it" }

fun List<MangaListResponse.Relationship>.cover256(mangaId: String) =
    cover(mangaId)?.let { "$it.256.jpg" }

fun List<MangaListResponse.Relationship>.cover512(mangaId: String) =
    cover(mangaId)?.let { "$it.512.jpg" }

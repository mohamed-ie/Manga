package com.manga.core.model.manga_dex.manga.request

import com.manga.core.model.manga_dex.common.MangaDexOrder
import com.manga.core.model.manga_dex.manga.MangaDexContentRating
import com.manga.core.model.manga_dex.common.MangaDexSortedOrder
import com.manga.core.model.manga_dex.common.MangaDexMode
import com.manga.core.model.manga_dex.manga.MangaDexPublicationDemographic
import com.manga.core.model.manga_dex.manga.MangaDexStatus
import kotlinx.datetime.Instant

data class MangaListRequest(
    val title: String? = null,
    val authorOrArtist: String? = null,
    val authors: List<String>? = null,
    val artists: List<String>? = null,
    val year: Int? = null,
    val includedTags: List<String>? = null,
    val includedTagsMode: MangaDexMode? = null,
    val excludedTags: List<String>? = null,
    val excludedTagsMode: MangaDexMode? = null,
    val status: List<MangaDexStatus>? = null,
    val originalLanguage: List<String>? = null,
    val excludedOriginalLanguage: List<String>? = null,
    val availableTranslatedLanguage: List<String>? = null,
    val publicationDemographic: List<MangaDexPublicationDemographic>? = null,
    val ids: List<String>? = null,
    val contentRating: List<MangaDexContentRating>? = null,
    val createdAtSince: Instant? = null,
    val updatedAtSince: Instant? = null,
    val order: List<MangaDexSortedOrder<Order>>? = null,
    val includes: List<MangaDexMangaIncludes>? = listOf(MangaDexMangaIncludes.CoverArt),
    val hasAvailableChapters: Boolean? = null,
    val group: String? = null,
    val offset: Int = 0,
    val limit: Int = 100
) {

    sealed class Order(override val value: String) : MangaDexOrder {
        data object Title : Order("title")
        data object Year : Order("year")
        data object CreatedAt : Order("createdAt")
        data object UpdatedAt : Order("updatedAt")
        data object LatestUploadedChapter : Order("latestUploadedChapter")
        data object FollowedCount : Order("followedCount")
        data object Relevance : Order("relevance")
        data object Rating : Order("rating")
    }
}

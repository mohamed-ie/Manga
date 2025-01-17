package com.manga.core.model.manga_dex.common

import com.manga.core.model.manga_dex.manga.MangaDexContentRating
import com.manga.core.model.manga_dex.manga.MangaDexPublicationDemographic
import com.manga.core.model.manga_dex.manga.MangaDexStatus
import kotlinx.datetime.Instant

data class MangaDexManga(
    val id: String,
    val title: MangaDexLocalizedString,
    val altTitles: List<MangaDexLocalizedString>,
    val isLocked: Boolean,
    val description: MangaDexLocalizedString,
    val links: List<MangaDexLink>,
    val originalLanguage: String,
    val lastVolume: String?,
    val lastChapter: String?,
    val publicationDemographic: MangaDexPublicationDemographic?,
    val status: MangaDexStatus,
    val year: Int?,
    val contentRating: MangaDexContentRating,
    val chapterNumbersResetOnNewVolume: Boolean,
    val availableTranslatedLanguages: List<String>,
    val latestUploadedChapter: String,
    val tags: List<MangaDexTag>,
    val state: MangaDexState,
    val createdAt: Instant,
    val updatedAt: Instant,
    val version: Int,
    val cover: MangaDexCover?
)
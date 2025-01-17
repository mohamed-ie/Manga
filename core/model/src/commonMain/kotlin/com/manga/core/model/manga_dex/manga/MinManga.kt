package com.manga.core.model.manga_dex.manga

import com.manga.core.model.manga_dex.chapter.MinChapter
import com.manga.core.model.manga_dex.common.MangaDexManga
import com.manga.core.model.manga_dex.common.MangaDexTag
import com.manga.core.model.manga_dex.common.cover256
import com.manga.core.model.manga_dex.statistics.MinMangaStatistics

data class MinManga(
    val id: String,
    val title: String,
    val description: String,
    val cover: String?,
    val status: MangaDexStatus?,
    val statistics: MinMangaStatistics?,
    val lastChapter: MinChapter?,
    val tags: Map<MangaDexTagGroup, List<MinTag>>,
    val publicationDemographic: MangaDexPublicationDemographic?,
)

fun MangaDexManga.asMinManga(
    statistics: MinMangaStatistics? = null,
    lastChapter: MinChapter? = null,
): MinManga = MinManga(
    id = id,
    title = title.value,
    description = description.value,
    cover = cover?.cover256(id),
    status = status,
    tags = tags.groupBy(MangaDexTag::group)
        .mapValues { (_, tags) -> tags.map(MangaDexTag::asMinTag) },
    publicationDemographic = publicationDemographic,
    statistics = statistics,
    lastChapter = lastChapter,
)
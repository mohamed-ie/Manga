package com.manga.core.model.manga

import kotlinx.datetime.Instant

data class MinManga(
    val id:String,
    val title: String,
    val cover: String?,
    val lastChapter: MangaChapter?,
    val status: Status,
    val updatedAt:Instant?,
    val publicationDemographic : MangaDexPublicationDemographic?,
)
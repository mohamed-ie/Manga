package com.manga.core.model.statistics

data class MinMangaStatistics(
    val mangaId: String,
    val rating: Float,
    val follows: Long
)

val MangaDexMangaStatistics.asMinStatistics
    get() = MinMangaStatistics(
        mangaId = mangaId,
        rating = rating.average ?: 0f,
        follows = follows,
    )
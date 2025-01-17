package com.manga.core.model.manga_dex.statistics

data class MangaDexMangaStatistics(
    val mangaId:String,
    val comments: Comments,
    val rating: Rating,
    val follows: Long
) {

    data class Comments(
        val threadId: Long,
        val repliesCount: Long,
    )

    data class Rating(
        val average: Float?,
        val bayesian: Float,
        val distribution: Map<String, Int> = emptyMap(),
    )
}
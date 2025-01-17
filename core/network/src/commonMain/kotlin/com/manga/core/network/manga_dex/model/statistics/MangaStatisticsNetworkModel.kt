package com.manga.core.network.manga_dex.model.statistics

import com.manga.core.model.manga_dex.statistics.MangaDexMangaStatistics
import kotlinx.serialization.Serializable

@Serializable
data class MangaStatisticsResponse(
    val result: String? = null,
    val statistics: Map<String, Statistics?>? = null,
) {
    @Serializable
    data class Statistics(
        val comments: Comments? = null,
        val rating: Rating? = null,
        val follows: Long? = null,
    )

    @Serializable
    data class Comments(
        val threadId: Long? = null,
        val repliesCount: Long? = null,
    )

    @Serializable
    data class Rating(
        val average: Float? = null,
        val bayesian: Float? = null,
        val distribution: Map<String, Int?> = emptyMap(),
    )
}

val MangaStatisticsResponse.asMangaDexModelList get(): List<MangaDexMangaStatistics> {
    return statistics?.mapNotNull { (id, statistic) ->
        MangaDexMangaStatistics(
            mangaId = id,
            comments = statistic?.comments?.asMangaDexModel ?: return@mapNotNull null,
            rating = statistic.rating?.asMangaDexModel ?: return@mapNotNull null,
            follows = statistic.follows ?: return@mapNotNull null
        )
    } ?: emptyList()
}

val MangaStatisticsResponse.Rating.asMangaDexModel: MangaDexMangaStatistics.Rating?
    get() {
        return MangaDexMangaStatistics.Rating(
            average = average,
            bayesian = bayesian ?: return null,
            distribution = distribution.mapValues { (_, count) -> count ?: 0 }
        )
    }
val MangaStatisticsResponse.Comments.asMangaDexModel: MangaDexMangaStatistics.Comments?
    get() {
        return MangaDexMangaStatistics.Comments(
            threadId = threadId ?: return null,
            repliesCount = repliesCount ?: return null,
        )
    }
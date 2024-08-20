package com.manga.core.network.response.statistics

import com.manga.core.common.ext.filterValuesNotNull
import kotlinx.serialization.Serializable
import com.manga.core.model.statistics.MangaStatistics

@Serializable
data class MangaStatisticsResponse(
    val result: String? = null,
    val statistics: Map<String, Statistics?>? = null,
    val response: String? = null
) {
    @Serializable
    data class Statistics(
        val comments: Comments? = null,
        val rating: Rating? = null,
        val follows: Int? = null,
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
        val distribution: Map<String, Int?>? = null,
    )
}

fun MangaStatisticsResponse.asExternalModel(): MangaStatistics? {
    return statistics?.filterValuesNotNull()?.run {
        val id = keys.firstOrNull() ?: return null
        MangaStatistics(
            id = keys.first(),
            rating = this[id]?.rating?.average ?: 0f,
            follows = this[id]?.follows ?: 0
        )
    }
}
package com.manga.core.model.manga

import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.Instant

data class Manga(
    val id: String,
    val cover: String?,
    val title: String,
    val description: String,
    val status: Status?,
    val statistics: Statistics?,
    val lastChapter: LastChapter?,
    val tags: ImmutableList<String>?,
    val demographic: Demographic?,
) {

    data class Statistics(
        val rating: Float,
        val follows: Long
    )

    data class LastChapter(
        val id: String,
        val name: String?,
        val readableAt: Instant
    )

    enum class Demographic { SHOUNEN, SHOUJO, JOSEI, SEINEN, NONE }

    enum class Status { ONGOING, COMPLETED, HIATUS, CANCELLED }
}
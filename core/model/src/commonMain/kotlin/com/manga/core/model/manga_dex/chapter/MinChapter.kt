package com.manga.core.model.manga_dex.chapter

import kotlinx.datetime.Instant

data class MinChapter(
    val id: String,
    val name: String?,
    val readableAt: Instant,
)


val MangaDexChapter.asMinChapter get(): MinChapter {
    return MinChapter(
        id = id,
        name = chapter,
        readableAt = readableAt,
    )
}
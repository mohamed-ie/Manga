package com.manga.core.model.chapter

import com.manga.core.model.common.MangaDexManga
import com.manga.core.model.common.MangaDexScanlationGroup
import com.manga.core.model.common.MangaDexUser
import kotlinx.datetime.Instant

data class MangaDexChapter(
    val id: String,
    val title: String?,
    val volume: String?,
    val chapter: String?,
    val pages: Int,
    val translatedLanguage: String,
    val externalUrl: String?,
    val version: Int,
    val createdAt: Instant,
    val publishAt: Instant,
    val updatedAt: Instant,
    val readableAt: Instant,
    val manga: MangaDexManga?,
    val mangaId: String,
    val scanlationGroup: MangaDexScanlationGroup?,
    val scanlationGroupId: String?,
    val user: MangaDexUser?,
    val userId: String?,
)
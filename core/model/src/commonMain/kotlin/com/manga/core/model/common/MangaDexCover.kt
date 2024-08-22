package com.manga.core.model.common

import kotlinx.datetime.Instant
import model.BuildConfig

data class MangaDexCover(
    val id: String,
    val volume: String?,
    val filename: String,
    val description: String? = null,
    val locale: String? = null,
    val version: Int,
    val createdAt: Instant,
    val updatedAt: Instant
)

fun MangaDexCover.cover256(mangaId: String) =
    "${BuildConfig.MANGA_DEX_COVER_URL}$mangaId/$filename.256.jpg"

fun MangaDexCover.cover512(mangaId: String) =
    "${BuildConfig.MANGA_DEX_COVER_URL}$mangaId/$filename.512.jpg"
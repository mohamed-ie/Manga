package com.manga.mangadex.network.model.common

import com.manga.source.mangadex.network.CommonBuildConfig
import kotlinx.datetime.Instant

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
    "${CommonBuildConfig.MANGA_DEX_COVER_URL}$mangaId/$filename.256.jpg"

fun MangaDexCover.cover512(mangaId: String) =
    "${CommonBuildConfig.MANGA_DEX_COVER_URL}$mangaId/$filename.512.jpg"
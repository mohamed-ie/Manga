package com.manga.core.model.manga_dex.common

import kotlinx.datetime.Instant

data class MangaDexScanlationGroup(
    val id: String,
    val name: String,
    val altNames: List<String>,
    val locked: Boolean?,
    val website: String?,
    val ircServer: String?,
    val ircChannel: String?,
    val discord: String?,
    val contactEmail: String?,
    val description: String?,
    val twitter: String?,
    val mangaUpdates: String?,
    val focusedLanguages: List<String>,
    val official: Boolean?,
    val verified: Boolean?,
    val inactive: Boolean?,
    val publishDelay: String?,
    val exLicensed: Boolean?,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val version: Int?
)


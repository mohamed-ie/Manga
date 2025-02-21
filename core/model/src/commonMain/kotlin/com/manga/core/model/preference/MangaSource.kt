package com.manga.core.model.preference

import kotlinx.serialization.Serializable

@Serializable
data class MangaSource(
    val name: String,
    val logo: String?
)
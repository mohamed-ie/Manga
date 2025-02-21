package com.manga.core.model.preference

import kotlinx.collections.immutable.persistentMapOf
import kotlinx.serialization.Serializable

@Serializable
data class MangaPreference(
    val language: String = "ar",
    val theme: MangaTheme = MangaTheme.SYSTEM,
    val shouldHideOnBoarding: Boolean = false,
    val currentSource: String = "",
    val sourcePreferences: Map<String,SourcePreference> = persistentMapOf()
)
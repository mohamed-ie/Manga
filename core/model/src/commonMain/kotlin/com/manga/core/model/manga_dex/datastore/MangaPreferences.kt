package com.manga.core.model.manga_dex.datastore

import com.manga.core.model.manga_dex.manga.MangaDexContentRating
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.serialization.Serializable

@Serializable
data class MangaPreferences(
    val language: String = "ar",
    val shouldHideOnBoarding: Boolean = false,
    val theme: Theme = Theme.SYSTEM,
    val chapterLanguageFilter: ImmutableSet<String> = persistentSetOf(),
    val originalLanguageFilter: ImmutableSet<String> = persistentSetOf(),
    val dataSaver: Boolean = false,
    val contentFilter: ImmutableSet<MangaDexContentRating> = persistentSetOf(MangaDexContentRating.SAFE),
    val showMangaWithoutChapters: Boolean = true,
    val filterTags: ImmutableSet<String> = persistentSetOf(),
) {
    enum class Theme {
        LIGHT, DARK, SYSTEM
    }
}
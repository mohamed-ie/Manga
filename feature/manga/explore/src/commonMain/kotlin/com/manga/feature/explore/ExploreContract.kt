package com.manga.feature.explore

import com.manga.core.model.manga.Manga

internal sealed interface ExploreEvent {
    data class OpenManga(val minManga: Manga) : ExploreEvent
    data class OpenChapter(val manga: Manga) : ExploreEvent
}
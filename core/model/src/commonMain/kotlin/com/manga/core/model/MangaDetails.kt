package com.manga.core.model

import com.manga.core.model.manga.Manga
import com.manga.core.model.manga.MangaVolume
import com.manga.core.model.statistics.MangaStatistics

data class MangaDetails(
    val manga: Manga,
    val volumes: List<MangaVolume>,
    val statistics: MangaStatistics
)

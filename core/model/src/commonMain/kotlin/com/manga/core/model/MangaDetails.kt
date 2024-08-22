package com.manga.core.model

import com.manga.core.model.common.MangaDexManga
import com.manga.core.model.manga.MangaVolume
import com.manga.core.model.statistics.MinMangaStatistics

data class MangaDetails(
    val manga: MangaDexManga,
    val volumes: List<MangaVolume>,
    val statistics: MinMangaStatistics
)

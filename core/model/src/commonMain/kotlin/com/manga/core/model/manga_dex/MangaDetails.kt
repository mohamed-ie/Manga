package com.manga.core.model.manga_dex

import com.manga.core.model.manga_dex.common.MangaDexManga
import com.manga.core.model.manga_dex.manga.MangaVolume
import com.manga.core.model.manga_dex.statistics.MinMangaStatistics

data class MangaDetails(
    val manga: MangaDexManga,
    val volumes: List<MangaVolume>,
    val statistics: MinMangaStatistics
)

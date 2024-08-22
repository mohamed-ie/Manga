package com.manga.core.model.manga

import com.manga.core.model.statistics.MinMangaStatistics

data class MinMangaWithStatistics(
    val minManga: MinManga,
    val statistics: MinMangaStatistics,
)
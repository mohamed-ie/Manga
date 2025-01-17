package com.manga.core.model.manga_dex.manga

import com.manga.core.model.manga_dex.statistics.MinMangaStatistics

data class MinMangaWithStatistics(
    val minManga: MinManga,
    val statistics: MinMangaStatistics,
)
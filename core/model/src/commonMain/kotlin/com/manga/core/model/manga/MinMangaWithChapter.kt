package com.manga.core.model.manga

import com.manga.core.model.chapter.MangaDexChapter
import com.manga.core.model.chapter.MinChapter

data class MinMangaWithChapter(
    val minManga: MinManga,
    val latestChapter: MinChapter
)
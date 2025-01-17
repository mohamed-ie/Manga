package com.manga.core.model.manga_dex.manga

import com.manga.core.model.manga_dex.chapter.MinChapter

data class MinMangaWithChapter(
    val minManga: MinManga,
    val latestChapter: MinChapter
)
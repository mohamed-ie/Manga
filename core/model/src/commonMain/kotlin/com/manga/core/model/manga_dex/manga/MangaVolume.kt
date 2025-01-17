package com.manga.core.model.manga_dex.manga

data class MangaVolume(
    val name: String,
    val chapters: List<MangaChapter>
)
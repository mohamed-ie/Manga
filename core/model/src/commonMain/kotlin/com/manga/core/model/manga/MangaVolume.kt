package com.manga.core.model.manga

data class MangaVolume(
    val name: String,
    val chapters: List<MangaChapter>
)
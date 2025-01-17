package com.manga.core.model.manga_dex.manga.request

data class MangaRequest(
    val id:String,
    val includes: List<MangaDexMangaIncludes>? = listOf(MangaDexMangaIncludes.CoverArt),
)


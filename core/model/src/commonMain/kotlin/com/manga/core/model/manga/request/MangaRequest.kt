package com.manga.core.model.manga.request

import com.manga.core.model.common.MangaDexIncludes

data class MangaRequest(
    val id:String,
    val includes: List<MangaDexMangaIncludes>? = listOf(MangaDexMangaIncludes.CoverArt),
)


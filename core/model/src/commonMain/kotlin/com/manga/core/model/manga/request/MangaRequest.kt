package com.manga.core.model.manga.request

import com.manga.core.model.manga.MangaDexIncludes


data class MangaRequest(
    val id:String,
    val includes: List<MangaDexIncludes>? = listOf(MangaDexIncludes.COVER_ART),
)


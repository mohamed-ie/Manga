package com.manga.core.model.manga_dex.chapter.request

data class ChapterRequest(
    val id:String,
    val includes:List<MangaDexChapterIncludes>? = null
)
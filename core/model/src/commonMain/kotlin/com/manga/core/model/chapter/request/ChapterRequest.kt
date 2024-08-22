package com.manga.core.model.chapter.request

data class ChapterRequest(
    val id:String,
    val includes:List<MangaDexChapterIncludes>? = null
)
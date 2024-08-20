package com.manga.core.model.chapter.request

import com.manga.core.model.chapter.ChapterInclude

data class ChapterRequest(
    val id:String,
    val includes:List<ChapterInclude>? = null
)
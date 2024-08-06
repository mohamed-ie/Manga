package chapter.request

import chapter.ChapterInclude

data class ChapterRequest(
    val id:String,
    val includes:List<ChapterInclude>? = null
)
package manga.request

import manga.MangaDexIncludes


data class MangaRequest(
    val id:String,
    val includes: List<MangaDexIncludes>? = listOf(MangaDexIncludes.COVER_ART),
)


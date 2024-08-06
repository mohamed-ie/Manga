package response.manga

import kotlinx.serialization.Serializable

@Serializable
data class MangaResponse(
    val result: String?,
    val response:String?,
    val data: MangaListResponse.Manga?
)
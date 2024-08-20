package com.manga.core.network.response.manga

import kotlinx.serialization.Serializable
import com.manga.core.model.manga.MangaChapter
import com.manga.core.model.manga.MangaVolume

@Serializable
data class MangaChaptersResponse(
    val result: String? = null,
    val volumes: Map<String, Volume?>? = null
) {
    @Serializable
    data class Volume(
        val volume: String? = null,
        val count: Int? = null,
        val chapters: Map<String, Chapter?>? = null
    )

    @Serializable
    data class Chapter(
        val chapter: String? = null,
        val id: String? = null,
        val others: List<String?>? = null,
        val count: Int? = null
    )
}

fun MangaChaptersResponse.Chapter.asExternalModel(): MangaChapter? {
    return MangaChapter(name = chapter ?: "", id = id ?: return null)
}

fun MangaChaptersResponse.Volume.asExternalModel(): MangaVolume {
    return MangaVolume(
        name = volume ?: "",
        chapters = chapters?.values
            ?.filterNotNull()
            ?.mapNotNull(MangaChaptersResponse.Chapter::asExternalModel)
            ?: emptyList()
    )
}
package com.manga.core.network.manga_dex.model.manga

import kotlinx.serialization.Serializable
import com.manga.core.model.manga_dex.manga.MangaChapter
import com.manga.core.model.manga_dex.manga.MangaVolume

@Serializable
data class MangaChaptersNetworkModel(
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

fun MangaChaptersNetworkModel.Chapter.asExternalModel(): MangaChapter? {
    return MangaChapter(name = chapter ?: "", id = id ?: return null)
}

fun MangaChaptersNetworkModel.Volume.asExternalModel(): MangaVolume {
    return MangaVolume(
        name = volume ?: "",
        chapters = chapters?.values
            ?.filterNotNull()
            ?.mapNotNull(MangaChaptersNetworkModel.Chapter::asExternalModel)
            ?: emptyList()
    )
}
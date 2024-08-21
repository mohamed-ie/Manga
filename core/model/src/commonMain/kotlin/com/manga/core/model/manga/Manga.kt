package com.manga.core.model.manga

import com.manga.core.model.chapter.Chapter

data class Manga(
    val id: String,
    val cover: String?,
    val titles: Map<String, String>,
    val descriptions: Map<String, String>,
    val tags: Map<MangaDexTagGroup, List<Tag>>,
    val availableTranslatedLanguages: List<String>,
    val status: MangaDexStatus?,
    val contentRating: MangaDexContentRating?,
    val publicationDemographic: MangaDexPublicationDemographic?,
    val latestUploadedChapterId:String?
) {
    data class Tag(
        val id: String,
        val name: Map<String, String>
    )
}

fun Manga.asMinManga(
    lastChapter: Chapter,
    languageTag: String
): MinManga = MinManga(
    id = id,
    title = titles.run { get(languageTag) ?: get("en") ?: firstNotNullOfOrNull { it.value } ?: "" },
    cover = cover,
    lastChapter = lastChapter,
    status = status,
    publicationDemographic = publicationDemographic
)
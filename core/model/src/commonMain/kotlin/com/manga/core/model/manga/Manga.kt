package com.manga.core.model.manga

data class Manga(
    val id: String,
    val cover: String?,
    val titles: Map<String, String>,
    val descriptions: Map<String, String>,
    val tags: Map<MangaDexTagGroup,List<Tag>>,
    val availableTranslatedLanguages: List<String>,
    val status: Status?,
    val contentRating: MangaDexContentRating?
) {
    data class Tag(
        val id: String,
        val name: Map<String,String>
    )
}
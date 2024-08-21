package com.manga.core.model.manga

data class NewReleaseManga(
    val id: String,
    val title: String,
    val description: String,
    val cover: String?,
    val tags: List<Manga.Tag>,
    val publicationDemographic: MangaDexPublicationDemographic
)

fun Manga.asNewReleaseManga(languageTag: String = "en") = NewReleaseManga(
    id = id,
    title = title(languageTag),
    cover = cover,
    description = description(languageTag),
    tags = tags[MangaDexTagGroup.GENRE] ?: emptyList(),
    publicationDemographic = publicationDemographic
)
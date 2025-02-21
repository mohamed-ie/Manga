package com.manga.mangadex.network.model.common

data class MangaDexTag(
    val id: String,
    val name: MangaDexLocalizedString,
    val description: MangaDexLocalizedString?,
    val group: MangaDexTagGroup,
    val version: Int
)
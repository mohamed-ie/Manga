package com.manga.core.model.manga_dex.common

import com.manga.core.model.manga_dex.manga.MangaDexTagGroup

data class MangaDexTag(
    val id: String,
    val name: MangaDexLocalizedString,
    val description: MangaDexLocalizedString?,
    val group: MangaDexTagGroup,
    val version: Int
)
package com.manga.core.model.common

import com.manga.core.model.manga.MangaDexTagGroup

data class MangaDexTag(
    val id: String,
    val name: MangaDexLocalizedString,
    val description: MangaDexLocalizedString?,
    val group: MangaDexTagGroup,
    val version: Int
)
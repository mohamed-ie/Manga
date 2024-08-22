package com.manga.core.model.manga

import com.manga.core.model.common.MangaDexTag

data class MinTag(
    val id: String,
    val name: String,
)

val MangaDexTag.asMinTag get() = MinTag(id = id, name = name.value)
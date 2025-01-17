package com.manga.core.model.manga_dex.common

data class MangaDexUser(
    val id: String,
    val username: String,
    val roles: List<String>,
    val version: Int?
)
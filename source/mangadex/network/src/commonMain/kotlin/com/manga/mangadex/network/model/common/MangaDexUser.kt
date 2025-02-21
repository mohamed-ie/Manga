package com.manga.mangadex.network.model.common

data class MangaDexUser(
    val id: String,
    val username: String,
    val roles: List<String>,
    val version: Int?
)
package com.manga.core.network.response.manga

import kotlinx.serialization.Serializable

@Serializable
data class MangaResponse(
    val result: String? = null,
    val response: String? = null,
    val data: MangaDto? = null
)
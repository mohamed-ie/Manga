package com.manga.core.model.preference

import com.manga.core.model.request.Request
import kotlinx.serialization.Serializable

@Serializable
data class SourcePreference(
    val source: MangaSource,
    val requests: Map<String, Request> = emptyMap(),
    val attributes: Map<String, String> = emptyMap()
)
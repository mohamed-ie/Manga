package com.manga.core.network.ktor

import io.ktor.http.*
import kotlinx.serialization.json.Json

data class KtorConfig(
    val url: String,
    val json: Json = Json { ignoreUnknownKeys = true },
    val contentType: ContentType = ContentType.Application.Json,
)
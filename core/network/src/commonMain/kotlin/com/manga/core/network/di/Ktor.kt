package com.manga.core.network.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import network.BuildConfig

@Single
internal fun httpClick() = HttpClient() {
    install(DefaultRequest) {
        url(BuildConfig.MANGA_DEX_URL)
        contentType(ContentType.Application.Json)
    }

    install(ContentNegotiation) {
        json(MangaJson)
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                println("HttpClient: $message")
            }
        }
        level = LogLevel.ALL
    }
}

private val MangaJson = Json {
    encodeDefaults = true
    isLenient = true
    allowSpecialFloatingPointValues = true
    allowStructuredMapKeys = true
    prettyPrint = false
    useArrayPolymorphism = false
    ignoreUnknownKeys = true
}
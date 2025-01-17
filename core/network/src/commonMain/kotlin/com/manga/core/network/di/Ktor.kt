package com.manga.core.network.di

import com.manga.core.network.manga_dex.model.common.relationships.*
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
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import network.CommonBuildConfig
import org.koin.core.annotation.Single
import sp.bvantur.inspektify.ktor.AutoDetectTarget
import sp.bvantur.inspektify.ktor.DataRetentionPolicy
import sp.bvantur.inspektify.ktor.InspektifyKtor

@Single
internal fun httpClick() = HttpClient {
    install(DefaultRequest) {
        url(CommonBuildConfig.MANGA_DEX_URL)
        contentType(ContentType.Application.Json)
    }

    install(ContentNegotiation) {
        json(MangaJson)
    }

    install(InspektifyKtor){
        shortcutEnabled = true
        logLevel = sp.bvantur.inspektify.ktor.LogLevel.All
        autoDetectEnabledFor = setOf(AutoDetectTarget.Android)
        dataRetentionPolicy = DataRetentionPolicy.SessionCount(4)
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
    prettyPrint = true
    useArrayPolymorphism = false
    ignoreUnknownKeys = true
    serializersModule = SerializersModule {
        polymorphic(baseClass = RelationshipNetworkModel::class) {
            subclass(MangaRelationship::class)
            subclass(ChapterRelationship::class)
            subclass(UserRelationship::class)
            subclass(ScanlationGroupRelationship::class)
            subclass(ChapterRelationship::class)
            defaultDeserializer { NoAttributeRelationship.serializer() }
        }
    }
}
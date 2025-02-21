package com.manga.core.network.di

import com.manga.core.network.ktor.KtorConfig
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.core.annotation.Single
import sp.bvantur.inspektify.ktor.AutoDetectTarget
import sp.bvantur.inspektify.ktor.DataRetentionPolicy
import sp.bvantur.inspektify.ktor.InspektifyKtor

@Single
internal fun httpClick(config: () -> KtorConfig) = HttpClient {
    install(DefaultRequest) {
        url(config().url)
        contentType(config().contentType)
    }

    install(ContentNegotiation) {
        json(config().json)
    }

    install(InspektifyKtor) {
        shortcutEnabled = true
        logLevel = sp.bvantur.inspektify.ktor.LogLevel.All
        autoDetectEnabledFor = setOf(AutoDetectTarget.Android)
        dataRetentionPolicy = DataRetentionPolicy.SessionCount(4)
    }
}
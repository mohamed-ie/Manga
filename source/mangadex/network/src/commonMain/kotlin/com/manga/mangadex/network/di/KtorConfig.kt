package com.manga.mangadex.network.di

import com.manga.core.network.ktor.KtorConfig
import com.manga.mangadex.network.model.common.relationships.ChapterRelationship
import com.manga.mangadex.network.model.common.relationships.CoverArtRelationship
import com.manga.mangadex.network.model.common.relationships.MangaRelationship
import com.manga.mangadex.network.model.common.relationships.NoAttributeRelationship
import com.manga.mangadex.network.model.common.relationships.RelationshipNetworkModel
import com.manga.mangadex.network.model.common.relationships.ScanlationGroupRelationship
import com.manga.mangadex.network.model.common.relationships.UserRelationship
import com.manga.source.mangadex.network.CommonBuildConfig
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.koin.core.annotation.Single

@Single
internal fun mangaDexKtorConfig(): () -> KtorConfig = {
    KtorConfig(
        url = CommonBuildConfig.MANGA_DEX_URL,
        json = Json {
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
                    subclass(CoverArtRelationship::class)
                    defaultDeserializer { NoAttributeRelationship.serializer() }
                }
            }
        }
    )
}
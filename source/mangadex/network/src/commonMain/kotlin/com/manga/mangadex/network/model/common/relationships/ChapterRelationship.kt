package com.manga.mangadex.network.model.common.relationships

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("chapter")
open class ChapterRelationship(val attributes: Attributes? = null) : RelationshipNetworkModel() {
    @Serializable
    data class Attributes(
        val volume: String? = null,
        val chapter: String? = null,
        val title: String? = null,
        val translatedLanguage: String? = null,
        val externalUrl: String? = null,
        val publishAt: String? = null,
        val readableAt: String? = null,
        val createdAt: String? = null,
        val updatedAt: String? = null,
        val pages: Int? = null,
        val version: Int? = null
    )
}
//
//fun ChapterRelationship.asExternalModel(
//    scanlationGroup: MangaDexScanlationGroup? = null,
//    manga: MangaDexManga? = null,
//    user: MangaDexUser? = null,
//    userId: String? = null,
//    mangaId: String? = null,
//    scanlationGroupId: String? = null,
//): MangaDexChapter? = attributes?.run {
//    MangaDexChapter(
//        id = id ?: return null,
//        chapter = chapter ?: return null,
//        translatedLanguage = translatedLanguage ?: return null,
//        publishAt = publishAt?.asInstant() ?: return null,
//        createdAt = createdAt?.asInstant() ?: return null,
//        updatedAt = updatedAt?.asInstant() ?: return null,
//        readableAt = readableAt?.asInstant() ?: return null,
//        version = version ?: return null,
//        pages = pages ?: return null,
//        title = title,
//        volume = volume,
//        externalUrl = externalUrl,
//        scanlationGroup = scanlationGroup,
//        manga = manga,
//        user = user,
//        mangaId = mangaId ?: return null,
//        scanlationGroupId = scanlationGroupId,
//        userId = userId
//    )
//}
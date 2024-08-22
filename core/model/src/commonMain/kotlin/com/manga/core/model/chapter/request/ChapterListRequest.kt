package com.manga.core.model.chapter.request

import com.manga.core.model.common.MangaDexOrder
import com.manga.core.model.manga.MangaDexContentRating
import com.manga.core.model.common.MangaDexIncludes
import com.manga.core.model.common.MangaDexSortedOrder
import kotlinx.datetime.Instant


data class ChapterListRequest(
    val title: String? = null,
    val groupIds: List<String>? = null,
    val ids: List<String>? = null,
    val uploaderIds: List<String>? = null,
    val mangaId: String? = null,
    val volumeIds: List<String>? = null,
    val chapterIds: List<String>? = null,
    val translatedLanguage: List<String>? = null,
    val originalLanguage: List<String>? = null,
    val excludedOriginalLanguage: List<String>? = null,
    val contentRating: List<MangaDexContentRating>? = null,
    val excludedGroupIds: List<String>? = null,
    val excludedUploaderIds: List<String>? = null,
    val includeFutureUpdates: Boolean? = null,
    val includeEmptyPages: Boolean? = null,
    val includeFuturePublishAt: Boolean? = null,
    val includeExternalUrl: Boolean? = null,
    val createdAtSince: Instant? = null,
    val updatedAtSince: Instant? = null,
    val publishAtSince: Instant? = null,
    val order: List<MangaDexSortedOrder<Order>>? = null,
    val includes: List<MangaDexChapterIncludes>? = null,
    val offset: Int = 0,
    val limit: Int = 100,
) {

    sealed class Order(override val value: String) : MangaDexOrder {
        data object CreatedAt : Order("createdAt")
        data object UpdatedAt : Order("updatedAt")
        data object ReadableAt : Order("readableAt")
        data object Volume : Order("volume")
        data object Chapter : Order("chapter")
    }
}
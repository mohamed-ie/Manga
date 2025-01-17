package com.manga.core.network.ktor.datasource

import com.manga.core.common.Resource
import com.manga.core.model.manga_dex.chapter.request.ChapterListRequest
import com.manga.core.model.manga_dex.chapter.request.ChapterRequest
import com.manga.core.network.datasource.MangaDexChapterNetworkDataSource
import com.manga.core.network.ktor.apiCall
import com.manga.core.network.manga_dex.model.MangaDexErrorNetworkModel
import com.manga.core.network.manga_dex.model.chapter.ChapterListNetworkModel
import com.manga.core.network.manga_dex.model.chapter.ChapterNetworkModel
import com.manga.core.network.manga_dex.model.common.MangaDexNetworkModel
import com.manga.core.network.manga_dex.model.common.MangaDexPageable
import com.manga.core.network.manga_dex.model.common.relationships.ChapterRelationship
import com.manga.core.network.manga_dex.model.manga.MangaNetworkModel
import com.manga.core.network.manga_dex.serverDateTimeFormat
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Single

@Single
internal class KtorMangaDexChapterNetwork(
    private val client: HttpClient
) : MangaDexChapterNetworkDataSource {
    override suspend fun chapter(request: ChapterRequest): Resource<MangaDexNetworkModel<ChapterRelationship>, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("chapter/${request.id}") {
                request.includes?.forEach { parameter("includes[]", it) }
            }
        }

    override suspend fun chapterList(request: ChapterListRequest): Resource<MangaDexPageable<ChapterRelationship>, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("chapter") {
                request.ids?.forEach { parameter("ids[]", it)}
                parameter("title", request.title)
                request.groupIds?.forEach { parameter("group[]", it)}
                request.uploaderIds?.forEach { parameter("uploader[]", it) }
                parameter("manga", request.mangaId)
                request.volumeIds?.forEach { parameter("volume[]", it) }
                request.chapterIds?.forEach { parameter("chapter[]", it)}
                request.translatedLanguage?.forEach { parameter("translatedLanguage[]", it)}
                request.originalLanguage?.forEach { parameter("originalLanguage[]", it)}
                request.excludedOriginalLanguage?.forEach { parameter("excludedOriginalLanguage[]", it)}
                request.contentRating?.forEach { parameter("contentRating[]", it)}
                request.excludedGroupIds?.forEach { parameter("excludedGroups[]", it)}
                request.excludedUploaderIds?.forEach { parameter("excludedUploaders[]", it)}
                parameter("includeFutureUpdates", request.includeFutureUpdates)
                parameter("includeEmptyPages", request.includeEmptyPages)
                parameter("includeFuturePublishAt", request.includeFuturePublishAt)
                parameter("includeExternalUrl", request.includeExternalUrl)
                parameter("createdAtSince", request.createdAtSince?.serverDateTimeFormat)
                parameter("updatedAtSince", request.updatedAtSince?.serverDateTimeFormat)
                parameter("publishAtSince", request.publishAtSince?.serverDateTimeFormat)
                request.order?.forEach { parameter("order[${it.order.value}]", it.mangaDexSortOrder.name.lowercase())}
                request.includes?.forEach { parameter("includes[]", it.value)}
                parameter("offset", request.offset)
                parameter("limit", request.limit)
            }
        }
}
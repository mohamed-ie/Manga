package com.manga.core.network.ktor.datasource

import com.manga.core.common.Resource
import com.manga.core.model.chapter.request.ChapterListRequest
import com.manga.core.model.chapter.request.ChapterRequest
import com.manga.core.network.datasource.MangaDexChapterNetworkDataSource
import com.manga.core.network.ktor.apiCall
import com.manga.core.network.response.MangaDexErrorResponse
import com.manga.core.network.response.chapter.ChapterListResponse
import com.manga.core.network.response.chapter.ChapterResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Single

@Single
internal class KtorMangaDexChapterNetwork(
    private val client: HttpClient
) : MangaDexChapterNetworkDataSource {
    override suspend fun chapter(request: ChapterRequest): Resource<ChapterResponse, MangaDexErrorResponse> =
        client.apiCall {
            get("chapter/${request.id}") {
                request.includes?.forEach { parameter("includes[]", it) }
            }
        }

    override suspend fun chapterList(request: ChapterListRequest): Resource<ChapterListResponse, MangaDexErrorResponse> =
        client.apiCall {
            get("chapter") {
                parameter("title", request.title)
                request.authors?.forEach { parameter("authors[]", it) }
                parameter("authorOrArtist", request.authorOrArtist)
                request.artists?.forEach { parameter("artists[]", it) }
                parameter("year", request.year)
                request.includedTags?.forEach { parameter("includedTags[]", it)}
                parameter("includedTagsMode", request.includedTagsMode)
                request.excludedTags?.forEach { parameter("excludedTags[]", it)}
                parameter("excludedTagsMode", request.excludedTagsMode)
                request.status?.forEach { parameter("status[]", it)}
                request.originalLanguage?.forEach { parameter("originalLanguage[]", it)}
                request.excludedOriginalLanguage?.forEach { parameter("excludedOriginalLanguage[]", it)}
                request.availableTranslatedLanguage?.forEach { parameter("availableTranslatedLanguage[]", it)}
                request.publicationDemographic?.forEach { parameter("publicationDemographic[]", it)}
                request.ids?.forEach { parameter("ids[]", it)}
                request.contentRating?.forEach { parameter("contentRating[]", it)}
                parameter("createdAtSince", request.createdAtSince)
                parameter("updatedAtSince", request.updatedAtSince)
                request.includes?.forEach { parameter("includes[]", it)}
                parameter("hasAvailableChapters", request.hasAvailableChapters)
                parameter("group", request.group)
                parameter("offset", request.offset)
                parameter("limit", request.limit)
            }
        }
}
package com.manga.core.network.ktor.datasource

import com.manga.core.common.Resource
import com.manga.core.model.manga_dex.manga.request.MangaChaptersRequest
import com.manga.core.model.manga_dex.manga.request.MangaListRequest
import com.manga.core.model.manga_dex.manga.request.MangaRequest
import com.manga.core.network.datasource.MangaDexMangaNetworkDataSource
import com.manga.core.network.ktor.apiCall
import com.manga.core.network.manga_dex.model.MangaDexErrorNetworkModel
import com.manga.core.network.manga_dex.model.common.MangaDexPageable
import com.manga.core.network.manga_dex.model.common.relationships.MangaRelationship
import com.manga.core.network.manga_dex.model.manga.MangaChaptersNetworkModel
import com.manga.core.network.manga_dex.model.manga.MangaListNetworkModel
import com.manga.core.network.manga_dex.model.manga.MangaNetworkModel
import com.manga.core.network.manga_dex.serverDateTimeFormat
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.koin.core.annotation.Single


@Single
internal class KtorMangaDexMangaNetwork(
    private val client: HttpClient
) : MangaDexMangaNetworkDataSource {
    override suspend fun mangaList(
        request: MangaListRequest
    ): Resource<MangaDexPageable<MangaRelationship>, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("manga") {
                request.order?.forEach {
                    parameter(
                        "order[${it.order.value}]",
                        it.mangaDexSortOrder.name.lowercase()
                    )
                }
                request.authors?.forEach { parameter("authors[]", it) }
                parameter("authorOrArtist", request.authorOrArtist)
                request.artists?.forEach { parameter("artists[]", it) }
                parameter("year", request.year)
                request.includedTags?.forEach { parameter("includedTags[]", it) }
                parameter("includedTagsMode", request.includedTagsMode)
                request.excludedTags?.forEach { parameter("excludedTags[]", it) }
                parameter("excludedTagsMode", request.excludedTagsMode)
                request.status?.forEach { parameter("status[]", it) }
                request.originalLanguage?.forEach { parameter("originalLanguage[]", it) }
                request.excludedOriginalLanguage?.forEach { parameter("excludedOriginalLanguage[]", it) }
                request.availableTranslatedLanguage?.forEach { parameter("availableTranslatedLanguage[]", it) }
                request.publicationDemographic?.forEach { parameter("publicationDemographic[]", it) }
                request.ids?.forEach { parameter("ids[]", it) }
                request.contentRating?.forEach { parameter("contentRating[]", it) }
                parameter("createdAtSince", request.createdAtSince?.serverDateTimeFormat)
                parameter("updatedAtSince", request.updatedAtSince?.serverDateTimeFormat)
                request.includes?.forEach { parameter("includes[]", it.value) }
                parameter("hasAvailableChapters", request.hasAvailableChapters)
                parameter("group", request.group)
                parameter("offset", request.offset)
                parameter("limit", request.limit)
            }
        }

    override suspend fun manga(request: MangaRequest): Resource<MangaNetworkModel, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("manga/${request.id}") {
                request.includes?.forEach { parameter("includes[]", it) }
            }
        }

    override suspend fun chapters(request: MangaChaptersRequest): Resource<MangaChaptersNetworkModel, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("manga/${request.id}/aggregate") {
                request.groups?.forEach { parameter("groups[]", it) }
                request.translatedLanguage?.forEach { parameter("translatedLanguage[]", it) }
            }
        }
}

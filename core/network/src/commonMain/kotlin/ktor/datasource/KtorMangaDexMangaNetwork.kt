package ktor.datasource

import core.common.Resource
import datasource.MangaDexMangaNetworkDataSource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import ktor.apiCall
import manga.request.MangaChaptersRequest
import manga.request.MangaListRequest
import manga.request.MangaRequest
import org.koin.core.annotation.Single
import response.MangaDexErrorResponse
import response.manga.MangaChaptersResponse
import response.manga.MangaListResponse
import response.manga.MangaResponse


@Single
internal class KtorMangaDexMangaNetwork(
    private val client: HttpClient
) : MangaDexMangaNetworkDataSource {
    override suspend fun mangaList(
        request: MangaListRequest
    ): Resource<MangaListResponse, MangaDexErrorResponse> =
        client.apiCall {
            get("manga") {
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

    override suspend fun manga(request: MangaRequest): Resource<MangaResponse, MangaDexErrorResponse> =
        client.apiCall {
            get("manga/${request.id}") {
                request.includes?.forEach { parameter("includes[]", it)}
            }
        }

    override suspend fun chapters(request: MangaChaptersRequest): Resource<MangaChaptersResponse, MangaDexErrorResponse> =
        client.apiCall {
            get("manga/${request.id}/aggregate") {
                request.groups?.forEach { parameter("groups[]", it)}
                request.translatedLanguage?.forEach { parameter("translatedLanguage[]", it)}
            }
        }
}

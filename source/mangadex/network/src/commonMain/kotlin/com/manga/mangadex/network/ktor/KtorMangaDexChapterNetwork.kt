package com.manga.mangadex.network.ktor

import com.manga.core.common.Resource
import com.manga.core.model.request.Request
import com.manga.core.model.request.RequestParameter
import com.manga.core.network.ktor.apiCall
import com.manga.core.network.ktor.parametersOf
import com.manga.mangadex.network.model.MangaDexErrorNetworkModel
import com.manga.mangadex.network.model.common.MangaDexNetworkModel
import com.manga.mangadex.network.model.common.MangaDexPageable
import com.manga.mangadex.network.model.common.relationships.ChapterRelationship
import com.manga.mangadex.network.datasource.MangaDexChapterNetworkDataSource
import com.manga.mangadex.network.model.serverDateTimeFormat
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.datetime.Instant
import org.koin.core.annotation.Single
import org.koin.core.parameter.parameterArrayOf

@Single
internal class KtorMangaDexChapterNetwork(
    private val client: HttpClient
) : MangaDexChapterNetworkDataSource {
    override suspend fun chapter(request: Request): Resource<MangaDexNetworkModel<ChapterRelationship>, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("chapter/${request["id"]}") {
                parametersOf(
                    request = request,
                    dateFormat = Instant::serverDateTimeFormat
                )
            }
        }

    override suspend fun chapterList(request: Request): Resource<MangaDexPageable<ChapterRelationship>, MangaDexErrorNetworkModel> =
        client.apiCall {
            get("chapter") {
                parametersOf(
                    request = request,
                    dateFormat = Instant::serverDateTimeFormat
                )
            }
        }
}
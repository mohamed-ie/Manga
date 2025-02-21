package com.manga.mangadex.network.datasource

import com.manga.core.common.Resource
import com.manga.core.model.request.Request
import com.manga.mangadex.network.model.MangaDexErrorNetworkModel
import com.manga.mangadex.network.model.at_home.AtHomeServerChapterNetworkModel

interface MangaDexAtHomeNetworkDataSource {
    suspend fun chapter(request: Request): Resource<AtHomeServerChapterNetworkModel, MangaDexErrorNetworkModel>
}
package com.manga.core.network.datasource

import com.manga.core.model.manga_dex.at_home.request.AtHomeServerChapterRequest
import com.manga.core.common.Resource
import com.manga.core.network.manga_dex.model.MangaDexErrorNetworkModel
import com.manga.core.network.manga_dex.model.at_home.AtHomeServerChapterNetworkModel

interface MangaDexAtHomeNetworkDataSource {
    suspend fun chapter(request: AtHomeServerChapterRequest): Resource<AtHomeServerChapterNetworkModel, MangaDexErrorNetworkModel>
}
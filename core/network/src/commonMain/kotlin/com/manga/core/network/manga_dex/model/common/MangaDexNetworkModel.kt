package com.manga.core.network.manga_dex.model.common

import com.manga.core.network.manga_dex.model.statistics.MangaStatisticsResponse
import kotlinx.serialization.Serializable

@Serializable
open class MangaDexNetworkModel<T>(
    open val response: String? = null,
    open val result: String? = null,
    open val data: T? = null
)
package com.manga.mangadex.network.model.common

import kotlinx.serialization.Serializable

@Serializable
open class MangaDexNetworkModel<T>(
    open val response: String? = null,
    open val result: String? = null,
    open val data: T? = null
)
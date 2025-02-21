package com.manga.core.data.provider.request_provider

import com.manga.core.model.request.Request

fun interface RequestProvider {
    suspend fun request(key: String): Request
}
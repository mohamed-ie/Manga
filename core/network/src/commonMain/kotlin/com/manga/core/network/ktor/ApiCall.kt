package com.manga.core.network.ktor

import com.manga.core.common.Resource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

internal suspend inline fun <reified D, reified E : Throwable> HttpClient.apiCall(
    block: HttpClient.() -> HttpResponse
): Resource<D, E> =
    try {
        val response = block()
        if (response.status.isSuccess())
            Resource.success(response.body<D>())
        else Resource.expectedFailure(response.body<E>())
    } catch (e: Throwable) {
        Resource.unexpectedFailure(e)
    }
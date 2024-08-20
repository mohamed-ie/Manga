package com.manga.core.network.response

import com.manga.core.model.MangaException
import kotlinx.serialization.Serializable

@Serializable
data class MangaDexErrorResponse(
    val result: String? = null,
    val errors: List<ErrorsItem?>? = null
) :Throwable(){
    @Serializable
    data class ErrorsItem(
        val context: String? = null,
        val id: String? = null,
        val detail: String? = null,
        val title: String? = null,
        val status: Int? = null
    )
}

fun MangaDexErrorResponse.asExternalModel(): MangaException = MangaException(
    message = errors?.firstOrNull { it?.detail != null }?.detail
)
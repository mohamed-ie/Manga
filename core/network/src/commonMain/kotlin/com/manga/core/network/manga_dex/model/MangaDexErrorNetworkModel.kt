package com.manga.core.network.manga_dex.model

import com.manga.core.model.manga_dex.common.MangaException
import kotlinx.serialization.Serializable

@Serializable
data class MangaDexErrorNetworkModel(
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

fun MangaDexErrorNetworkModel.asExternalModel(): MangaException = MangaException(
    message = errors?.firstOrNull { it?.detail != null }?.detail
)
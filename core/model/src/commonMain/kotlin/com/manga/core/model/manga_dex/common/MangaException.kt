package com.manga.core.model.manga_dex.common

data class MangaException(
    override val message: String? = null
) : Throwable(message = message)
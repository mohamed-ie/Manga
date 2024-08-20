package com.manga.core.model

data class MangaException(
    override val message: String? = null
) : Throwable(message = message)
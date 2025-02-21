package com.manga.core.model.common

data class MangaException(
    override val message: String? = null
) : Throwable(message = message)
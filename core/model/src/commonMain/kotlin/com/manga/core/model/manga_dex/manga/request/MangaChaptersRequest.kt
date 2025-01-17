package com.manga.core.model.manga_dex.manga.request

data class MangaChaptersRequest(
    val id:String,
    val groups:List<String>? = null,
    val translatedLanguage:List<String>? = null,
)
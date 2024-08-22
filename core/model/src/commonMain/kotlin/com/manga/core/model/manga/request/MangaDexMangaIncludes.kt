package com.manga.core.model.manga.request

import com.manga.core.model.common.MangaDexIncludes

sealed class MangaDexMangaIncludes(override val value: String): MangaDexIncludes {
    data object Manga :MangaDexMangaIncludes("manga")
    data object CoverArt : MangaDexMangaIncludes("cover_art")
    data object Author : MangaDexMangaIncludes("author")
    data object Artist : MangaDexMangaIncludes("artist")
    data object Tag : MangaDexMangaIncludes("tag")
    data object Creator : MangaDexMangaIncludes("creator")
}
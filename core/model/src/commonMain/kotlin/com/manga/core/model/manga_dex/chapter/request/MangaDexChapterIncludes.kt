package com.manga.core.model.manga_dex.chapter.request

import com.manga.core.model.manga_dex.common.MangaDexIncludes

sealed class MangaDexChapterIncludes(override val value: String): MangaDexIncludes {
    data object Manga: MangaDexChapterIncludes("chapter")
    data object ScanlationGroup: MangaDexChapterIncludes("scanlation_group")
    data object User: MangaDexChapterIncludes("user")
}
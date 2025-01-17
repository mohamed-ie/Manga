package com.manga.core.data.repository.manga_central

import com.manga.core.common.IntPageable
import com.manga.core.common.Resource
import com.manga.core.model.manga_dex.common.MangaException
import com.manga.core.model.manga_dex.chapter.request.ChapterListRequest
import com.manga.core.model.manga_dex.manga.MinManga
import com.manga.core.model.manga_dex.manga.request.MangaListRequest

interface MangaCentralRepository {
    suspend fun minMangaList(
        request: MangaListRequest,
        withStatistics: Boolean = false
    ): Resource<IntPageable<MinManga>, MangaException>

    suspend fun minMangaList(
        request: ChapterListRequest,
        withStatistics: Boolean = false
    ): Resource<IntPageable<MinManga>, MangaException>
}
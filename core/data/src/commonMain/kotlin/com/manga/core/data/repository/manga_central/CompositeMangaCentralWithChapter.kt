package com.manga.core.data.repository.manga_central

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.manga.core.data.repository.chapter.ChapterRepository
import com.manga.core.data.repository.manga.MangaRepository
import com.manga.core.model.manga.MinManga
import com.manga.core.model.manga.request.MangaListRequest
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
internal class CompositeMangaCentralWithChapter(
    private val mangaRepository: MangaRepository,
    private val chapterRepository: ChapterRepository
) : MangaCentralRepository {
    override fun minManga(
        mangaListRequest: MangaListRequest
    ): Flow<PagingData<MinManga>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            MinMangePagingSource(
                mangaRepository = mangaRepository,
                chapterRepository = chapterRepository,
                mangaListRequest = mangaListRequest,
                languageTag = "en"
            )
        }
    ).flow

}
package com.manga.core.data.repository.manga_central

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.manga.core.common.Resource
import com.manga.core.common.map
import com.manga.core.common.mapExpected
import com.manga.core.data.repository.chapter.ChapterRepository
import com.manga.core.data.repository.manga.MangaRepository
import com.manga.core.model.MangaException
import com.manga.core.model.manga.MinManga
import com.manga.core.model.manga.NewReleaseManga
import com.manga.core.model.manga.asNewReleaseManga
import com.manga.core.model.manga.request.MangaListRequest
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
internal class CompositeMangaCentralWithChapter(
    private val mangaRepository: MangaRepository,
    private val chapterRepository: ChapterRepository
) : MangaCentralRepository {
    override fun minMangaList(
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

    override suspend fun newReleaseManga(mangaListRequest: MangaListRequest): Resource<List<NewReleaseManga>, MangaException> =
        mangaRepository.mangaList(mangaListRequest)
            .map { (manga) -> manga.map { it.asNewReleaseManga() } }
}
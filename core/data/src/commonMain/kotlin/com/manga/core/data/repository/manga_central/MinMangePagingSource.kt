package com.manga.core.data.repository.manga_central

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.manga.core.common.getOrThrow
import com.manga.core.data.repository.chapter.ChapterRepository
import com.manga.core.data.repository.manga.MangaRepository
import com.manga.core.model.chapter.request.ChapterListRequest
import com.manga.core.model.manga.MinManga
import com.manga.core.model.manga.asMinManga
import com.manga.core.model.manga.request.MangaListRequest

internal class MinMangePagingSource(
    private val mangaRepository: MangaRepository,
    private val chapterRepository: ChapterRepository,
    private val mangaListRequest: MangaListRequest,
    private val languageTag: String = "en"
) : PagingSource<Int, MinManga>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MinManga> {
        try {
            val offset = params.key ?: 0

            val pageableMangaList = mangaRepository.mangaList(mangaListRequest.copy(offset = offset))
                .getOrThrow()

            val mangaList = pageableMangaList.data

            val chapterRequest = ChapterListRequest(ids = mangaList.mapNotNull { it.latestUploadedChapterId })

            val chapterList = chapterRepository.chapterList(chapterRequest)
                .getOrThrow()
                .data

            val data = mangaList.zip(chapterList) { manga, chapter ->
                manga.asMinManga(chapter, languageTag)
            }

            return LoadResult.Page(
                data = data,
                prevKey = pageableMangaList.previousKey,
                nextKey = pageableMangaList.nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MinManga>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(mangaListRequest.limit) ?: anchorPage?.nextKey?.minus(mangaListRequest.limit)
        }
    }
}
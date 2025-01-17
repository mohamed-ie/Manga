package com.manga.core.data.repository.manga_central

//import com.manga.core.model.manga.asMinManga

//
//internal class MinMangePagingSource(
//    private val mangaRepository: MangaRepository,
//    private val chapterRepository: ChapterRepository,
//    private val mangaListRequest: MangaListRequest,
//    private val languageTag: String = "en"
//) : PagingSource<Int, MinManga>() {
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MinManga> {
//        try {
//            val offset = params.key ?: 0
//
//            val pageableMangaList = mangaRepository.mangaList(mangaListRequest.copy(offset = offset))
//                .getOrThrow()
//
//            val mangaList = pageableMangaList.data
//
//            val chapterRequest = ChapterListRequest(ids = mangaList.mapNotNull { it.latestUploadedChapterId })
//
//            val chapterList = chapterRepository.chapterList(chapterRequest)
//                .getOrThrow()
//                .data
//
//            val data = mangaList.zip(chapterList) { manga, chapter ->
//                manga.asMinManga(chapter, languageTag)
//            }
//
//            return LoadResult.Page(
//                data = data,
//                prevKey = pageableMangaList.previousKey,
//                nextKey = pageableMangaList.nextKey
//            )
//        } catch (e: Exception) {
//            return LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, MinManga>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(mangaListRequest.limit) ?: anchorPage?.nextKey?.minus(mangaListRequest.limit)
//        }
//    }
//}
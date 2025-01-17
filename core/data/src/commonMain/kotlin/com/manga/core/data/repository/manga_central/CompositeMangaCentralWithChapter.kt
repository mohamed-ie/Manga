package com.manga.core.data.repository.manga_central

import com.manga.core.common.*
import com.manga.core.data.repository.chapter.ChapterRepository
import com.manga.core.data.repository.manga.MangaRepository
import com.manga.core.data.repository.statistics.StatisticsRepository
import com.manga.core.model.manga_dex.common.MangaException
import com.manga.core.model.manga_dex.chapter.asMinChapter
import com.manga.core.model.manga_dex.chapter.request.ChapterListRequest
import com.manga.core.model.manga_dex.common.MangaDexManga
import com.manga.core.model.manga_dex.manga.MinManga
import com.manga.core.model.manga_dex.manga.asMinManga
import com.manga.core.model.manga_dex.manga.request.MangaListRequest
import com.manga.core.model.manga_dex.statistics.asMinStatistics
import com.manga.core.model.manga_dex.statistics.request.MangaListStatisticsRequest
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.koin.core.annotation.Single

@Single
internal class CompositeMangaCentralWithChapter(
    private val mangaRepository: MangaRepository,
    private val chapterRepository: ChapterRepository,
    private val statisticsRepository: StatisticsRepository
) : MangaCentralRepository {

    override suspend fun minMangaList(request: MangaListRequest, withStatistics: Boolean) =
        if (withStatistics.not()) mangaRepository.mangaList(request).map { it.map(MangaDexManga::asMinManga) }

        else coroutineScope {
            try {
                val mangaPageable = mangaRepository.mangaList(request).getOrThrow()
                val mangaList = mangaPageable.data
                val mangaIds = mangaList.map { it.id }
                val mangaListStatisticsRequest = MangaListStatisticsRequest(ids = mangaIds)

                val statisticsList =
                    async { statisticsRepository.mangaList(mangaListStatisticsRequest) }
                        .await()
                        .getOrThrow()

                val minMangaList = mangaList.zip(statisticsList) { manga, statistics ->
                    manga.asMinManga(statistics = statistics.asMinStatistics)
                }

                Resource.pageable(mangaPageable.replaceData(minMangaList))
            } catch (e: MangaException) {
                Resource.expectedFailure(e)
            } catch (t: Throwable) {
                Resource.unexpectedFailure(t)
            }
        }

    override suspend fun minMangaList(
        request: ChapterListRequest,
        withStatistics: Boolean
    ): Resource<IntPageable<MinManga>, MangaException> = coroutineScope {
        try {
            val chapterPageable = chapterRepository.chapterList(request)
                .getOrThrow()

            val chapterList = chapterPageable.data.ifEmpty { return@coroutineScope Resource.success(emptyPageable()) }

            val mangaIds = chapterList.map { it.mangaId }
            val mangaRequest = MangaListRequest(ids = mangaIds)
            val mangaList = mangaRepository.mangaList(mangaRequest).getOrThrow().data
            var minMangaList = mangaList.zip(chapterList) { manga, chapter ->
                manga.asMinManga(lastChapter = chapter.asMinChapter)
            }

            if (withStatistics.not())
                return@coroutineScope Resource.pageable(chapterPageable.replaceData(minMangaList))

            val mangaListStatisticsRequest = MangaListStatisticsRequest(ids = mangaIds)

            val statisticsList =
                async { statisticsRepository.mangaList(mangaListStatisticsRequest) }
                    .await()
                    .getOrThrow()

            minMangaList = minMangaList.zip(statisticsList) { manga, statistics ->
                manga.copy(statistics = statistics.asMinStatistics)
            }

            Resource.pageable(chapterPageable.replaceData(minMangaList))
        } catch (e: MangaException) {
            Resource.expectedFailure(e)
        } catch (t: Throwable) {
            Resource.unexpectedFailure(t)
        }
    }
}
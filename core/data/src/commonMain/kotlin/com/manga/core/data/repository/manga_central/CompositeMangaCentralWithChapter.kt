package com.manga.core.data.repository.manga_central

import com.manga.core.common.Resource
import com.manga.core.common.getOrThrow
import com.manga.core.common.map
import com.manga.core.data.repository.chapter.ChapterRepository
import com.manga.core.data.repository.manga.MangaRepository
import com.manga.core.data.repository.statistics.StatisticsRepository
import com.manga.core.model.MangaException
import com.manga.core.model.chapter.asMinChapter
import com.manga.core.model.chapter.request.ChapterListRequest
import com.manga.core.model.common.MangaDexManga
import com.manga.core.model.manga.MinManga
import com.manga.core.model.manga.asMinManga
import com.manga.core.model.manga.request.MangaListRequest
import com.manga.core.model.statistics.asMinStatistics
import com.manga.core.model.statistics.request.MangaListStatisticsRequest
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
        if (withStatistics.not())
            mangaRepository.mangaList(request).map { it.data.map(MangaDexManga::asMinManga) }
        else coroutineScope {
            try {
                val mangaList = mangaRepository.mangaList(request).getOrThrow().data
                val mangaIds = mangaList.map { it.id }
                val mangaListStatisticsRequest = MangaListStatisticsRequest(ids = mangaIds)

                val statisticsList =
                    async { statisticsRepository.mangaList(mangaListStatisticsRequest) }
                        .await()
                        .getOrThrow()

                val minMangaList = mangaList.zip(statisticsList) { manga, statistics ->
                    manga.asMinManga(statistics = statistics.asMinStatistics)
                }

                Resource.success(minMangaList)
            } catch (e: MangaException) {
                Resource.expectedFailure(e)
            } catch (t: Throwable) {
                Resource.unexpectedFailure(t)
            }
        }

    override suspend fun minMangaList(
        request: ChapterListRequest,
        withStatistics: Boolean
    ): Resource<List<MinManga>, MangaException> = coroutineScope {
        try {
            val chapterList = chapterRepository.chapterList(request)
                .getOrThrow()
                .data

            val mangaIds = chapterList.map { it.mangaId }
            val mangaRequest = MangaListRequest(ids = mangaIds)
            val mangaList = mangaRepository.mangaList(mangaRequest).getOrThrow().data
            var minMangaList = mangaList.zip(chapterList) { manga, chapter ->
                manga.asMinManga(lastChapter = chapter.asMinChapter)
            }

            if (withStatistics.not())
                return@coroutineScope Resource.success(minMangaList)

            val mangaListStatisticsRequest = MangaListStatisticsRequest(ids = mangaIds)

            val statisticsList =
                async { statisticsRepository.mangaList(mangaListStatisticsRequest) }
                    .await()
                    .getOrThrow()

            minMangaList = minMangaList.zip(statisticsList) { manga, statistics ->
                manga.copy(statistics = statistics.asMinStatistics)
            }

            Resource.success(minMangaList)
        } catch (e: MangaException) {
            Resource.expectedFailure(e)
        } catch (t: Throwable) {
            Resource.unexpectedFailure(t)
        }
    }
}
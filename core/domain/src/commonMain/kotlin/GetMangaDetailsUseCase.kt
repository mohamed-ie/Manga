package core.use_case

import MangaDetails
import MangaException
import core.common.Resource
import core.common.getOrThrow
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import manga.MangaRepository
import manga.request.MangaChaptersRequest
import manga.request.MangaRequest
import org.koin.core.annotation.Factory
import statistics.StatisticsRepository
import statistics.request.MangaStatisticsRequest

@Factory
class GetMangaDetailsUseCase(
    private val mangaRepository: MangaRepository,
    private val statisticsRepository: StatisticsRepository,
) {
    suspend operator fun invoke(mangaId: String): Resource<MangaDetails, MangaException> =
        coroutineScope {
            val manga = async { mangaRepository.manga(MangaRequest(mangaId)).getOrThrow() }
            val statistics = async { statisticsRepository.manga(MangaStatisticsRequest(mangaId)).getOrThrow() }
            val volumes = async { mangaRepository.chapters(MangaChaptersRequest(mangaId)).getOrThrow() }
            try {
                Resource.success(
                    MangaDetails(
                        manga = manga.await(),
                        volumes = volumes.await(),
                        statistics = statistics.await()
                    )
                )
            } catch (e: MangaException) {
                Resource.expectedFailure(e)
            } catch (e: Throwable) {
                Resource.unexpectedFailure(e)
            }
        }
}
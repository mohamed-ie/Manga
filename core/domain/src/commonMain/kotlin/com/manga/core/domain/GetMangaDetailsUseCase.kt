//package com.manga.core.domain
//
//import com.manga.core.model.manga_dex.MangaDetails
//import com.manga.core.model.common.MangaException
//import com.manga.core.common.Resource
//import com.manga.core.common.getOrThrow
//import kotlinx.coroutines.async
//import kotlinx.coroutines.coroutineScope
//import com.manga.core.data.repository_old.statistics.StatisticsRepository
//import com.manga.core.model.manga_dex.manga.request.MangaChaptersRequest
//import com.manga.core.model.manga_dex.manga.request.MangaRequest
//import org.koin.core.annotation.Factory
//import com.manga.core.model.manga_dex.statistics.asMinStatistics
//import com.manga.core.model.manga_dex.statistics.request.MangaStatisticsRequest
//
//@Factory
//class GetMangaDetailsUseCase(
//    private val mangaRepository: com.manga.core.data.repository_old.manga.MangaRepository,
//    private val statisticsRepository: StatisticsRepository,
//) {
//    suspend operator fun invoke(mangaId: String): Resource<MangaDetails, MangaException> =
//        coroutineScope {
//            val manga = async { mangaRepository.manga(com.manga.core.model.manga_dex.manga.request.MangaRequest(mangaId)).getOrThrow() }
//            val statistics = async { statisticsRepository.manga(MangaStatisticsRequest(mangaId)).getOrThrow() }
//            val volumes = async { mangaRepository.chapters(MangaChaptersRequest(mangaId)).getOrThrow() }
//            try {
//                Resource.success(
//                    MangaDetails(
//                        manga = manga.await()!!,
//                        volumes = volumes.await(),
//                        statistics = statistics.await().asMinStatistics
//                    )
//                )
//            } catch (e: MangaException) {
//                Resource.expectedFailure(e)
//            } catch (e: Throwable) {
//                Resource.unexpectedFailure(e)
//            }
//        }
//}
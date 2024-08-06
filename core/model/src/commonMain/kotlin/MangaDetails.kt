import manga.Manga
import manga.MangaVolume
import statistics.MangaStatistics

data class MangaDetails(
    val manga: Manga,
    val volumes: List<MangaVolume>,
    val statistics: MangaStatistics
)

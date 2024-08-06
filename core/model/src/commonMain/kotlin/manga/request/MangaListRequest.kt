package manga.request

import manga.MangaDexContentRating
import manga.MangaDexIncludes
import manga.MangaDexMangaOrder
import manga.MangaDexMode
import manga.MangaDexPublicationDemographic
import manga.Status

data class MangaListRequest(
    val title: String? = null,
    val authorOrArtist: String? = null,
    val authors: List<String>? = null,
    val artists: List<String>? = null,
    val year: Int? = null,
    val includedTags: List<String>? = null,
    val includedTagsMode: MangaDexMode? = null,
    val excludedTags: List<String>? = null,
    val excludedTagsMode: MangaDexMode? = null,
    val status: List<Status>? = null,
    val originalLanguage: List<String>? = null,
    val excludedOriginalLanguage: List<String>? = null,
    val availableTranslatedLanguage: List<String>? = null,
    val publicationDemographic: List<MangaDexPublicationDemographic>? = null,
    val ids: List<String>? = null,
    val contentRating: List<MangaDexContentRating>? = listOf(MangaDexContentRating.SAFE),
    val createdAtSince: String? = null,
    val updatedAtSince: String? = null,
    val order: List<MangaDexMangaOrder>? = null,
    val includes: List<MangaDexIncludes>? = listOf(MangaDexIncludes.COVER_ART),
    val hasAvailableChapters: Boolean? = null,
    val group: String? = null,
    val offset: Int = 0,
    val limit: Int = 100
)

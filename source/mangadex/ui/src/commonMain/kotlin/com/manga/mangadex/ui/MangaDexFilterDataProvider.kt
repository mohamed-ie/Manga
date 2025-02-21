package com.manga.mangadex.ui

import com.manga.core.data.provider.FilterDataProvider
import com.manga.core.data.repository.preference.PreferenceRepository
import com.manga.core.model.filter.FilterData
import com.manga.core.model.filter.FilterData.Date
import com.manga.core.model.filter.FilterData.Fill
import com.manga.core.model.filter.FilterData.MultiChoice
import com.manga.core.model.filter.FilterData.MultiFill
import com.manga.core.model.filter.FilterData.Order
import com.manga.core.model.filter.FilterData.Range
import com.manga.core.model.filter.FilterData.SingleChoice
import com.manga.core.model.filter.filterData
import com.manga.mangadex.ui.di.MANGA_DEX_KEY
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import manga.source.mangadex.ui.generated.resources.Res
import manga.source.mangadex.ui.generated.resources.manga_dex_artists
import manga.source.mangadex.ui.generated.resources.manga_dex_author_or_artist
import manga.source.mangadex.ui.generated.resources.manga_dex_authors
import manga.source.mangadex.ui.generated.resources.manga_dex_available_translated_language
import manga.source.mangadex.ui.generated.resources.manga_dex_content_rating
import manga.source.mangadex.ui.generated.resources.manga_dex_content_rating_safe
import manga.source.mangadex.ui.generated.resources.manga_dex_created_at_since
import manga.source.mangadex.ui.generated.resources.manga_dex_excluded_original_language
import manga.source.mangadex.ui.generated.resources.manga_dex_excluded_tags
import manga.source.mangadex.ui.generated.resources.manga_dex_excluded_tags_mode
import manga.source.mangadex.ui.generated.resources.manga_dex_group
import manga.source.mangadex.ui.generated.resources.manga_dex_has_available_chapters
import manga.source.mangadex.ui.generated.resources.manga_dex_ids
import manga.source.mangadex.ui.generated.resources.manga_dex_included_artist
import manga.source.mangadex.ui.generated.resources.manga_dex_included_author
import manga.source.mangadex.ui.generated.resources.manga_dex_included_cover_art
import manga.source.mangadex.ui.generated.resources.manga_dex_included_creator
import manga.source.mangadex.ui.generated.resources.manga_dex_included_manga
import manga.source.mangadex.ui.generated.resources.manga_dex_included_tag
import manga.source.mangadex.ui.generated.resources.manga_dex_included_tags
import manga.source.mangadex.ui.generated.resources.manga_dex_included_tags_mode
import manga.source.mangadex.ui.generated.resources.manga_dex_includes
import manga.source.mangadex.ui.generated.resources.manga_dex_limit
import manga.source.mangadex.ui.generated.resources.manga_dex_mode_and
import manga.source.mangadex.ui.generated.resources.manga_dex_mode_or
import manga.source.mangadex.ui.generated.resources.manga_dex_no
import manga.source.mangadex.ui.generated.resources.manga_dex_original_language
import manga.source.mangadex.ui.generated.resources.manga_dex_publication_demographic
import manga.source.mangadex.ui.generated.resources.manga_dex_publication_demographic_josei
import manga.source.mangadex.ui.generated.resources.manga_dex_publication_demographic_none
import manga.source.mangadex.ui.generated.resources.manga_dex_publication_demographic_seinen
import manga.source.mangadex.ui.generated.resources.manga_dex_publication_demographic_shoujo
import manga.source.mangadex.ui.generated.resources.manga_dex_publication_demographic_shounen
import manga.source.mangadex.ui.generated.resources.manga_dex_status
import manga.source.mangadex.ui.generated.resources.manga_dex_status_cancelled
import manga.source.mangadex.ui.generated.resources.manga_dex_status_completed
import manga.source.mangadex.ui.generated.resources.manga_dex_status_hiatus
import manga.source.mangadex.ui.generated.resources.manga_dex_status_ongoing
import manga.source.mangadex.ui.generated.resources.manga_dex_title
import manga.source.mangadex.ui.generated.resources.manga_dex_updated_at_since
import manga.source.mangadex.ui.generated.resources.manga_dex_year
import manga.source.mangadex.ui.generated.resources.manga_dex_yes
import org.jetbrains.compose.resources.getString
import org.koin.core.annotation.Single
@Single
internal class MangaDexFilterDataProvider(
    preferenceRepository: PreferenceRepository
) : FilterDataProvider {
    private val source =
        preferenceRepository.preference.map { it.sourcePreferences[MANGA_DEX_KEY] }

    override suspend fun manga(): ImmutableMap<String, FilterData<*>> {
        val request = source.first()?.requests?.get("manga")
        return persistentMapOf(
            "includes" to MultiChoice(
                name = getString(Res.string.manga_dex_includes),
                options = persistentMapOf(
                    "cover_art" to getString(Res.string.manga_dex_included_cover_art),
                    "manga" to getString(Res.string.manga_dex_included_manga),
                    "author" to getString(Res.string.manga_dex_included_author),
                    "artist" to getString(Res.string.manga_dex_included_artist),
                    "tag" to getString(Res.string.manga_dex_included_tag),
                    "creator" to getString(Res.string.manga_dex_included_creator)
                ),
                value = request?.getList("includes")?.map(Any?::toString)?.toImmutableSet()
            )
        )
    }

    override suspend fun mangaList(): ImmutableMap<String, FilterData<*>> {
        val request = source.first()?.requests?.get("mangaList")

        return persistentMapOf(
            "title" to Fill(name = getString(Res.string.manga_dex_title)),
            "authorOrArtist" to Fill(name = getString(Res.string.manga_dex_author_or_artist)),
            "limit" to Range(name = getString(Res.string.manga_dex_limit), min = 10, max = 100),
            "authors" to MultiFill(name = getString(Res.string.manga_dex_authors)),
            "artists" to MultiFill(name = getString(Res.string.manga_dex_artists)),
            "year" to Date(name = getString(Res.string.manga_dex_year), pick = Date.Pick.YEAR),
            "includedTags" to MultiFill(name = getString(Res.string.manga_dex_included_tags)),
            "excludedTags" to MultiFill(name = getString(Res.string.manga_dex_excluded_tags)),
            "originalLanguage" to MultiFill(name = getString(Res.string.manga_dex_original_language)),
            "excludedOriginalLanguage" to MultiFill(name = getString(Res.string.manga_dex_excluded_original_language)),
            "availableTranslatedLanguage" to MultiFill(name = getString(Res.string.manga_dex_available_translated_language)),
            "ids" to MultiFill(name = getString(Res.string.manga_dex_ids)),
            "group" to Fill(name = getString(Res.string.manga_dex_group)),
            "createdAtSince" to Date(
                name = getString(Res.string.manga_dex_created_at_since),
                pick = Date.Pick.DATE_TIME
            ),
            "updatedAtSince" to Date(
                name = getString(Res.string.manga_dex_updated_at_since),
                pick = Date.Pick.DATE_TIME
            ),
            "hasAvailableChapters" to SingleChoice(
                name = getString(Res.string.manga_dex_has_available_chapters),
                options = persistentMapOf(
                    "true" to getString(Res.string.manga_dex_yes),
                    "false" to getString(Res.string.manga_dex_no)
                )
            ),
            "contentRating" to MultiChoice(
                name = getString(Res.string.manga_dex_content_rating),
                options = persistentMapOf("safe" to getString(Res.string.manga_dex_content_rating_safe)),
                value = persistentSetOf("safe")
            ),
            "order" to Order(
                name = getString(Res.string.manga_dex_author_or_artist),
                options = persistentListOf(),
                descending = true
            ), "includedTagsMode" to SingleChoice(
                name = getString(Res.string.manga_dex_included_tags_mode),
                options = persistentMapOf(
                    "OR" to getString(Res.string.manga_dex_mode_or),
                    "AND" to getString(Res.string.manga_dex_mode_and)
                )
            ),
            "publicationDemographic" to MultiChoice(
                name = getString(Res.string.manga_dex_publication_demographic),
                options = persistentMapOf(
                    "shounen" to getString(Res.string.manga_dex_publication_demographic_shounen),
                    "shoujo" to getString(Res.string.manga_dex_publication_demographic_shoujo),
                    "josei" to getString(Res.string.manga_dex_publication_demographic_josei),
                    "seinen" to getString(Res.string.manga_dex_publication_demographic_seinen),
                    "none" to getString(Res.string.manga_dex_publication_demographic_none)
                )
            ),
            "excludedTagsMode" to SingleChoice(
                name = getString(Res.string.manga_dex_excluded_tags_mode),
                options = persistentMapOf(
                    "OR" to getString(Res.string.manga_dex_mode_or),
                    "AND" to getString(Res.string.manga_dex_mode_and)
                )
            ),
            "status" to MultiChoice(
                name = getString(Res.string.manga_dex_status),
                options = persistentMapOf(
                    "ongoing" to getString(Res.string.manga_dex_status_ongoing),
                    "completed" to getString(Res.string.manga_dex_status_completed),
                    "hiatus" to getString(Res.string.manga_dex_status_hiatus),
                    "cancelled" to getString(Res.string.manga_dex_status_cancelled)
                )
            )
        )
            .mapValues { (key, value) -> request?.filterData(key, value) ?: value }
            .toImmutableMap()
    }
}
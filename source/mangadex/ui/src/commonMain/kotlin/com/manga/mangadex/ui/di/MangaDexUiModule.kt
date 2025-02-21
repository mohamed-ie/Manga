package com.manga.mangadex.ui.di

import com.manga.core.common.di.ApplicationScope
import com.manga.core.data.repository.preference.PreferenceRepository
import com.manga.core.model.preference.MangaSource
import com.manga.core.model.preference.SourcePreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

@Single(createdAtStart = true)
internal fun mangaDexUi(
    @ApplicationScope scope: CoroutineScope,
    preferenceRepository: PreferenceRepository
) {
    scope.launch {
        preferenceRepository.updateSource(
            key = MANGA_DEX_KEY,
            transform = {
                it ?: SourcePreference(
                    source = MangaSource(
                        name = "MangaDex",
                        logo = "https://mangadex.org/img/brand/mangadex-logo.svg"
                    )
                )
            }
        )
    }
}

internal const val MANGA_DEX_KEY = "manga_dex"
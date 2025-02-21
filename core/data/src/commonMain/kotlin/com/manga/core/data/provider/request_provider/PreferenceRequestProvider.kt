package com.manga.core.data.provider.request_provider

import com.manga.core.datastore.MangaPreferenceDataSource
import com.manga.core.model.preference.MangaPreference
import com.manga.core.model.request.Request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single

@Single
internal class PreferenceRequestProvider(
    private val preferenceDataSource: MangaPreferenceDataSource
) : RequestProvider {
    private val preference: Flow<MangaPreference> get() = preferenceDataSource.preference

    private suspend fun currentSource() = preference.first().currentSource

    override suspend fun request(key: String): Request =
        preferenceDataSource.preference
            .first()
            .sourcePreferences[currentSource()]
            ?.requests
            ?.get(key)
            ?: Request()
}
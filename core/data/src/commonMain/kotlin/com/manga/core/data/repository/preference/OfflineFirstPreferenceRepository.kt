package com.manga.core.data.repository.preference

import com.manga.core.datastore.MangaPreferenceDataSource
import com.manga.core.model.preference.MangaPreference
import com.manga.core.model.preference.SourcePreference
import com.manga.core.model.request.Request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.koin.core.annotation.Single

@Single
internal class OfflineFirstPreferenceRepository(
    private val preferenceDataSource: MangaPreferenceDataSource
) : PreferenceRepository {
    override val preference: Flow<MangaPreference> = preferenceDataSource.preference

    private suspend fun currentSource() = preference.first().currentSource

    override suspend fun request(key: String): Request =
        preferenceDataSource.preference
            .first()
            .sourcePreferences[currentSource()]
            ?.requests
            ?.get(key)
            ?: Request()

    override suspend fun updateSource(
        key: String,
        transform: suspend (SourcePreference?) -> SourcePreference
    ) {
        preferenceDataSource.update { oldPreference ->
            val updatedSourcePreference = oldPreference.sourcePreferences.toMutableMap()
            updatedSourcePreference[key] = transform(updatedSourcePreference[key])
            oldPreference.copy(sourcePreferences = updatedSourcePreference)
        }
    }

    override suspend fun update(transform: (MangaPreference) -> MangaPreference) {
        preferenceDataSource.update(transform)
    }
}
package com.manga.core.data.repository.preference

import com.manga.core.model.preference.MangaPreference
import com.manga.core.model.preference.SourcePreference
import com.manga.core.model.request.Request
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    val preference: Flow<MangaPreference>
    suspend fun request(key: String): Request
    suspend fun update(transform: (MangaPreference) -> MangaPreference)
    suspend fun updateSource(key: String, transform:suspend (SourcePreference?) -> SourcePreference)
}
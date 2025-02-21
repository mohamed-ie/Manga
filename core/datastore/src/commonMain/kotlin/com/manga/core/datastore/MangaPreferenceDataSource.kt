package com.manga.core.datastore

import androidx.datastore.core.DataStore
import com.manga.core.datastore.di.MangaDataStore
import com.manga.core.model.preference.MangaPreference
import org.koin.core.annotation.Single

@Single
class MangaPreferenceDataSource(
    @MangaDataStore
    val dataStore: DataStore<MangaPreference>
) {
    val preference = dataStore.data

    suspend fun update(transform:suspend (MangaPreference) -> MangaPreference) {
        dataStore.updateData(transform = transform)
    }
}
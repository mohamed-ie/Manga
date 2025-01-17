package com.manga.core.datastore

import androidx.datastore.core.DataStore
import com.manga.core.datastore.di.PatenteDataStore
import com.manga.core.model.manga_dex.datastore.MangaPreferences
import org.koin.core.annotation.Single

@Single
class MangaPreferencesDataSource(
    @PatenteDataStore
    private val dataStore: DataStore<MangaPreferences>
) {
    val preference = dataStore.data

    suspend fun setLocale(locale: String) {
        dataStore.updateData { it.copy(language = locale) }
    }

    suspend fun setShouldHideOnBoarding(shouldHide: Boolean) {
        dataStore.updateData { it.copy(shouldHideOnBoarding = shouldHide) }
    }
}
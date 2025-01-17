package com.manga.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.manga.core.common.di.Dispatcher
import com.manga.core.common.di.ApplicationScope
import com.manga.core.datastore.MangaPreferencesSerializer
import com.manga.core.model.manga_dex.datastore.MangaPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import okio.FileSystem
import okio.Path
import org.koin.core.annotation.Qualifier
import org.koin.core.annotation.Single

@Qualifier
internal annotation class DataStoreProducePath

@Qualifier
internal annotation class PatenteDataStore

@Single
@PatenteDataStore
internal fun createDataStore(
    @DataStoreProducePath
    producePath: () -> Path,
    patentePreferenceSerializer: MangaPreferencesSerializer,
    @Dispatcher.IO ioDispatcher: CoroutineDispatcher,
    @ApplicationScope scope: CoroutineScope,
): DataStore<MangaPreferences> =
    DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = patentePreferenceSerializer,
            producePath = producePath
        ),
        scope = CoroutineScope(scope.coroutineContext + ioDispatcher)
    )

internal const val dataStoreFileName = "manga.preferences_pb"
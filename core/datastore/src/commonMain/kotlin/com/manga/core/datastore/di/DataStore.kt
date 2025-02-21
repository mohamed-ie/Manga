package com.manga.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.manga.core.common.di.ApplicationScope
import com.manga.core.common.di.Dispatcher
import com.manga.core.datastore.MangaPreferenceSerializer
import com.manga.core.model.preference.MangaPreference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import okio.FileSystem
import okio.Path
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Named
internal annotation class DataStoreProducePath

@Named
internal annotation class MangaDataStore

@Single
@MangaDataStore
internal fun createDataStore(
    serializer: MangaPreferenceSerializer,
    @DataStoreProducePath producePath: () -> Path,
    @Dispatcher.IO ioDispatcher: CoroutineDispatcher,
    @ApplicationScope scope: CoroutineScope,
): DataStore<MangaPreference> =
    DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = serializer,
            producePath = producePath
        ),
        scope = CoroutineScope(scope.coroutineContext + ioDispatcher)
    )

internal const val DATA_STORE_PRODUCE_FILE = "manga.preferences_pb"
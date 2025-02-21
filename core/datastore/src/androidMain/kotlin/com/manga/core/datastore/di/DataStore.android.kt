package com.manga.core.datastore.di

import android.content.Context
import okio.Path
import okio.Path.Companion.toOkioPath
import org.koin.core.annotation.Single

@Single
@DataStoreProducePath
internal fun dataStoreProducePath(context: Context): () -> Path =
    { context.filesDir.resolve(DATA_STORE_PRODUCE_FILE).toOkioPath() }
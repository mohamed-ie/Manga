package com.manga.core.datastore

import androidx.datastore.core.okio.OkioSerializer
import com.manga.core.model.manga_dex.datastore.MangaPreferences
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import okio.BufferedSink
import okio.BufferedSource
import okio.IOException
import org.koin.core.annotation.Single

@Single
internal class MangaPreferencesSerializer : OkioSerializer<MangaPreferences> {
    override val defaultValue = MangaPreferences()

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun readFrom(source: BufferedSource): MangaPreferences {
        try {
            return ProtoBuf.decodeFromByteArray<MangaPreferences>(source.readByteArray())
        } catch (exception: IOException) {
            throw Exception(exception.message ?: "Serialization Exception")
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun writeTo(t: MangaPreferences, sink: BufferedSink) {
        sink.write(ProtoBuf.encodeToByteArray(t))
    }
}
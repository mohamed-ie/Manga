package com.manga.core.datastore

import androidx.datastore.core.okio.OkioSerializer
import com.manga.core.model.preference.MangaPreference
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf
import okio.BufferedSink
import okio.BufferedSource
import okio.IOException
import org.koin.core.annotation.Single

@Single
internal class MangaPreferenceSerializer : OkioSerializer<MangaPreference> {
    override val defaultValue = MangaPreference()

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun readFrom(source: BufferedSource): MangaPreference {
        try {
            return ProtoBuf.decodeFromByteArray<MangaPreference>(source.readByteArray())
        } catch (exception: IOException) {
            throw Exception(exception.message ?: "Serialization Exception")
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun writeTo(t: MangaPreference, sink: BufferedSink) {
        sink.write(ProtoBuf.encodeToByteArray(t))
    }
}
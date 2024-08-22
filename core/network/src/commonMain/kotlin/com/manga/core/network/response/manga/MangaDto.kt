package com.manga.core.network.response.manga

import com.manga.core.model.common.MangaDexCover
import com.manga.core.model.common.MangaDexManga
import com.manga.core.network.response.common.CoverArtRelationship
import com.manga.core.network.response.common.MangaRelationship
import com.manga.core.network.response.common.RelationshipDto
import com.manga.core.network.response.common.asMangaDexCover
import com.manga.core.network.response.common.asMangaDexModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("manga")
data class MangaDto(
    val relationships: List<RelationshipDto?>? = null,
) : MangaRelationship()

val MangaDto.asMangaDexModel
    get() :MangaDexManga? {
        var cover: MangaDexCover? = null
        relationships?.forEach { relationship ->
            when (relationship) {
                is CoverArtRelationship -> cover = relationship.asMangaDexCover(relationship.id)
                else -> Unit
            }
        }
        return asMangaDexModel(cover = cover)
    }
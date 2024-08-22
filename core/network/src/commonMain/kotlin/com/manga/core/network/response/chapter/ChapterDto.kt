package com.manga.core.network.response.chapter

import com.manga.core.model.chapter.MangaDexChapter
import com.manga.core.model.chapter.request.MangaDexChapterIncludes
import com.manga.core.model.common.MangaDexManga
import com.manga.core.model.common.MangaDexScanlationGroup
import com.manga.core.model.common.MangaDexUser
import com.manga.core.network.response.common.ChapterRelationship
import com.manga.core.network.response.common.MangaRelationship
import com.manga.core.network.response.common.NoAttributeRelationship
import com.manga.core.network.response.common.RelationshipDto
import com.manga.core.network.response.common.ScanlationGroupRelationship
import com.manga.core.network.response.common.UserRelationship
import com.manga.core.network.response.common.asMangaDexModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("chapter")
data class ChapterDto(
    val relationships: List<RelationshipDto?>? = null,
) : ChapterRelationship()


fun ChapterDto.asExternalModel(): MangaDexChapter? {
    var manga: MangaDexManga? = null
    var scanlationGroup: MangaDexScanlationGroup? = null
    var user: MangaDexUser? = null
    var mangaId: String? = null
    var scanlationGroupId: String? = null
    var userId: String? = null

    relationships?.forEach { relationship ->
        when (relationship) {
            is ScanlationGroupRelationship -> {
                scanlationGroup = relationship.asMangaDexModel()
                scanlationGroupId = relationship.id
            }
            is MangaRelationship ->{
                manga = relationship.asMangaDexModel()
                mangaId = relationship.id
            }
            is UserRelationship ->{
                user = relationship.asMangaDexModel()
                userId = relationship.id
            }
            else -> Unit
        }
    }

    return asMangaDexModel(
        scanlationGroup = scanlationGroup,
        manga = manga,
        user = user,
        userId = userId,
        mangaId = mangaId,
        scanlationGroupId = scanlationGroupId
    )
}
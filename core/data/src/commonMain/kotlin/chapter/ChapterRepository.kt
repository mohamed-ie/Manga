package chapter

import MangaException
import chapter.request.ChapterRequest
import core.common.Resource

interface ChapterRepository {
    suspend fun chapter(request: ChapterRequest):Resource<Chapter,MangaException>
}
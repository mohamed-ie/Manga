package at_home

import MangaException
import at_home.request.AtHomeServerChapterRequest
import core.common.Resource

interface AtHomeRepository {
    suspend fun chapter(request: AtHomeServerChapterRequest): Resource<AtHomeChapter, MangaException>
}


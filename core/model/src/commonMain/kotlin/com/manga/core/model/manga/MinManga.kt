package com.manga.core.model.manga

import com.manga.core.model.chapter.Chapter

data class MinManga(
    val id:String,
    val title: String,
    val cover: String?,
    val lastChapter: Chapter?,
    val status: MangaDexStatus?,
    val publicationDemographic : MangaDexPublicationDemographic?,
)
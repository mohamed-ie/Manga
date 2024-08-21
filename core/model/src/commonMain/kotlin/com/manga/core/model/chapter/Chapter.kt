package com.manga.core.model.chapter

import kotlinx.datetime.Instant

data class Chapter(
    val id: String,
    val name:String,
    val publishAt:Instant
)
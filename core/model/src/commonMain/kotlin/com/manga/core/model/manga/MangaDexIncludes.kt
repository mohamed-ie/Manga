package com.manga.core.model.manga

enum class MangaDexIncludes {
    MANGA, COVER_ART, AUTHOR, ARTIST, TAG, CREATOR;

    override fun toString(): String {
        return super.toString().lowercase()
    }
}
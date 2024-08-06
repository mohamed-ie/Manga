package com.manga.database

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package com.manga.core.model.manga

data class MangaDexMangaOrder(
    val order: Order,
    val desc: Boolean = true
) {
    enum class Order(val value: String) {
        TITLE("title"),
        YEAR("year"),
        CREATED_AT("createdAt"),
        UPDATED_AT("updatedAt"),
        LATEST_UPLOADED_CHAPTER("latestUploadedChapter"),
        FOLLOW_COUNT("followCount"),
        RELEVANCE("relevance"),
        RATING("rating");
    }

    override fun toString(): String {
        return "order[${order.value}]=${if (desc) "DESC" else "ASC"}"
    }
}
package com.manga.core.model.categories.request

data class CategoriesRequest(
    val query: String? = null,
    val requiresAuthentication: Boolean = false,
    val offset: Int = 0,
    val limit: Int = 20
)
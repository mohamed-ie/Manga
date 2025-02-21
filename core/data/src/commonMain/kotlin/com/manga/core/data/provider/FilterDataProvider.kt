package com.manga.core.data.provider

import com.manga.core.model.filter.FilterData
import kotlinx.collections.immutable.ImmutableMap

interface FilterDataProvider {
    suspend fun manga(): ImmutableMap<String, FilterData<*>>
    suspend fun mangaList(): ImmutableMap<String, FilterData<*>>
}
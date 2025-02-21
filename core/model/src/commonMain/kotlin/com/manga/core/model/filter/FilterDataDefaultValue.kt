package com.manga.core.model.filter

import com.manga.core.model.filter.FilterData.Date
import com.manga.core.model.filter.FilterData.Fill
import com.manga.core.model.filter.FilterData.MultiChoice
import com.manga.core.model.filter.FilterData.MultiFill
import com.manga.core.model.filter.FilterData.Order
import com.manga.core.model.filter.FilterData.Range
import com.manga.core.model.filter.FilterData.SingleChoice
import com.manga.core.model.request.Request
import kotlinx.collections.immutable.toImmutableSet
import kotlinx.datetime.Instant

fun Request.filterData(key: String, filterData: FilterData<*>) =
    when (filterData) {
        is Date -> filterData.copy(value = get<Instant>(key))
        is Fill -> filterData.copy(value = get<String>(key))
        is MultiChoice -> filterData.copy(value = getList(key)?.map(Any?::toString)?.toImmutableSet())
        is MultiFill -> filterData.copy(value = getList(key)?.map(Any?::toString)?.toImmutableSet())
        is Range<*> -> filterData.copy(value = get(key))
        is SingleChoice -> filterData.copy(value = get<String>(key))
        is Order -> filterData.copy(
            value = getMap(key)?.keys?.first(),
            descending = getMap(key)?.values?.first()?.toString()?.toBoolean() ?: true
        )
    }
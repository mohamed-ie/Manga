package com.manga.core.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.TimeZone

interface TimeZoneMonitor {
    val currentTimeZone: Flow<TimeZone>
}

object NoOpTimeZoneMonitor : TimeZoneMonitor {
    override val currentTimeZone: Flow<TimeZone> = flowOf(TimeZone.currentSystemDefault())
}
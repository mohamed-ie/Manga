package com.manga.core.data.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface NetworkMonitor {
    val isOnline: Flow<Boolean>
}
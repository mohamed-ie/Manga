package com.manga.core.data.di

import com.manga.core.data.utils.NoOpNetworkMonitor
import org.koin.core.annotation.Single

@Single
internal fun networkMonitor() = NoOpNetworkMonitor
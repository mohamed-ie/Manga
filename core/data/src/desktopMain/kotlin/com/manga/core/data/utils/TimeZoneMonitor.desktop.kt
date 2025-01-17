package com.manga.core.data.utils

import org.koin.core.annotation.Single

@Single
fun timeZoneMonitor() = NoOpTimeZoneMonitor
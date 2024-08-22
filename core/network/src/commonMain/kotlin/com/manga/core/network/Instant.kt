package com.manga.core.network

import kotlinx.datetime.Instant
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.char

internal val Instant.serverDateTimeFormat
    get() = format(DateTimeComponents.Format {
        year()
        char('-')
        monthNumber()
        char('-')
        dayOfMonth()
        char('T')
        hour()
        char(':')
        minute()
        char(':')
        second()
    })


internal fun String.asInstant(): Instant =Instant.parse(this)
package core.ui.com.manga.core.ui

import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.text_now
import manga.core.ui.generated.resources.text_short_days_ago
import manga.core.ui.generated.resources.text_short_hours_ago
import manga.core.ui.generated.resources.text_short_minutes_ago
import manga.core.ui.generated.resources.text_short_months_ago
import manga.core.ui.generated.resources.text_short_weeks_ago
import manga.core.ui.generated.resources.text_short_years_ago
import org.jetbrains.compose.resources.stringResource

val Instant.relativeTime
    @Composable
    get() = periodUntil(Clock.System.now(), TimeZone.currentSystemDefault()).run {
        if (years > 1)
            return@run stringResource(Res.string.text_short_years_ago, years)

        if (months > 1)
            return@run stringResource(Res.string.text_short_months_ago, months)

        if (days > 6)
            return@run stringResource(Res.string.text_short_weeks_ago, days % 7)

        if (days > 1)
            return@run stringResource(Res.string.text_short_days_ago, days)

        if (hours > 1)
            return@run stringResource(Res.string.text_short_hours_ago, hours)

        if (minutes > 1)
            return@run stringResource(Res.string.text_short_minutes_ago, minutes)

        stringResource(Res.string.text_now)
    }
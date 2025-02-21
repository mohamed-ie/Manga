package com.manga.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_text_back_online
import manga.core.ui.generated.resources.core_ui_text_no_internet_connection
import org.jetbrains.compose.resources.stringResource

@Composable
fun NetworkStatusIndicator(modifier: Modifier = Modifier, isOffline: Boolean) =
    AnimatedVisibility(
        modifier = modifier,
        visible = isOffline,
        enter = expandVertically(expandFrom = Alignment.Top),
        exit = shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(delayMillis = 1000)
        )
    ) {
        val color by animateColorAsState(
            targetValue = if (isOffline) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer,
            label = "internet container color"
        )

        val contentColor by animateColorAsState(
            targetValue = if (isOffline) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer,
            label = "internet content color"
        )

        Surface(
            color = color,
            contentColor = contentColor
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = stringResource(if (isOffline) Res.string.core_ui_text_no_internet_connection else Res.string.core_ui_text_back_online),
                textAlign = TextAlign.Center
            )
        }
    }

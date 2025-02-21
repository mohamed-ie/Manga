package com.manga.core.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_action_retry
import org.jetbrains.compose.resources.stringResource


@Composable
fun ErrorContent(
    message: String,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) = Column(
    modifier = modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
) {
    Text(text = message)
    Spacer(Modifier.height(16.dp))
    TextButton(onClick = onRetry) {
        Text(text = stringResource(Res.string.core_ui_action_retry))
    }
}
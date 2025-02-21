package com.manga.core.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_text_no_content
import org.jetbrains.compose.resources.stringResource


@Composable
fun EmptyContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Text(text = stringResource(Res.string.core_ui_text_no_content))
    }
}
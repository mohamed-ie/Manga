package com.manga.feature.onboarding.state

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.manga.core.ui.component.MangaAsyncImage
import com.manga.feature.onboarding.OnboardingUiState

@Composable
internal fun SuccessState(
    uiState: OnboardingUiState.Success,
    modifier: Modifier = Modifier,
    onSourceSelected: (String) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        uiState.sources.forEach { (key, source) ->
            item(key = key) {
                ListItem(
                    modifier = Modifier.clickable { onSourceSelected(key) },
                    headlineContent = { Text(source.name) },
                    leadingContent = {
                        MangaAsyncImage(
                            modifier = Modifier.size(48.dp),
                            model = source.logo,
                            contentScale = ContentScale.Inside,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}
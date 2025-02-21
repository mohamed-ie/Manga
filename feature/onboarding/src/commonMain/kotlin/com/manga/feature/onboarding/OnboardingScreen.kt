package com.manga.feature.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.manga.core.ui.content.AnimatedScreenContent
import com.manga.core.ui.MangaScreenDefaults
import com.manga.feature.onboarding.OnboardingEvent.SourceSelected
import com.manga.feature.onboarding.OnboardingUiState.Loading
import com.manga.feature.onboarding.OnboardingUiState.Success
import com.manga.feature.onboarding.state.SuccessState

@Composable
internal fun OnboardingScreen(
    uiState: OnboardingUiState,
    onEvent: (OnboardingEvent) -> Unit
) {
    AnimatedScreenContent(targetState = uiState) { targetState ->
        when (targetState) {
            is Loading -> MangaScreenDefaults.LoadingState()

            is Success -> SuccessState(
                uiState = targetState,
                modifier = Modifier.fillMaxSize(),
                onSourceSelected = {onEvent(SourceSelected(it))}
            )
        }
    }
}
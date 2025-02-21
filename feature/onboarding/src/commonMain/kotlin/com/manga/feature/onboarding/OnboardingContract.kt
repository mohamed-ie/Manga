package com.manga.feature.onboarding

import com.manga.core.model.preference.MangaSource
import kotlinx.collections.immutable.ImmutableMap

internal sealed interface OnboardingUiEvent {
    data object NavigateToMainScreen : OnboardingUiEvent
}

internal sealed interface OnboardingEvent {
    data class SourceSelected(val sourceKey: String) : OnboardingEvent
}

internal sealed interface OnboardingUiState {
    data object Loading : OnboardingUiState
    data class Success(val sources: ImmutableMap<String, MangaSource>) : OnboardingUiState
}
package com.manga.feature.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.utils.app_event.ObserveAppEvents
import com.manga.feature.onboarding.OnboardingUiEvent.NavigateToMainScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun OnboardingRoute(
    navigateToMainScreen: () -> Unit,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAppEvents<OnboardingUiEvent> { event ->
        when (event) {
            NavigateToMainScreen -> navigateToMainScreen()
        }
    }

    OnboardingScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent
    )
}
package com.manga.feature.onboarding

import androidx.lifecycle.viewModelScope
import com.manga.core.data.repository.preference.PreferenceRepository
import com.manga.core.ui.BaseViewModel
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class OnboardingViewModel(
    private val preferenceRepository: PreferenceRepository
) : BaseViewModel() {
    val uiState = preferenceRepository.preference
        .map { preference ->
            OnboardingUiState.Success(
                sources = preference.sourcePreferences
                    .mapValues { (_, sourcePreference) -> sourcePreference.source }
                    .toImmutableMap()
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = OnboardingUiState.Loading
        )

    fun onEvent(event: OnboardingEvent) {
        when (event) {
            is OnboardingEvent.SourceSelected -> setSource(event.sourceKey)
        }
    }

    private fun setSource(source: String) {
        viewModelScope.launch {
            preferenceRepository.update { it.copy(currentSource = source) }
            send(OnboardingUiEvent.NavigateToMainScreen)
        }
    }
}
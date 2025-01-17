package com.manga.feature.authentication.login

import androidx.lifecycle.ViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
internal class LoginViewModel() : ViewModel() {
   private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<MessagingUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.PasswordChanged ->
                _uiState.update { it.copy(password = event.password) }

            is LoginEvent.PhoneNumberChanged ->
                _uiState.update { it.copy(phoneNumber = event.phoneNumber) }

            LoginEvent.Login -> processLogin()
        }
    }

    private fun processLogin() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            _uiState.update { it.copyValidating() }

            if (_uiState.value.containsError) {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            login()
        }
    }

    private suspend fun login() {

    }
}

private suspend fun LoginUiState.copyValidating() =
    copy(
        phoneNumberError = when {
            RequiredValidator(phoneNumber).not() -> getString(Res.string.core_ui_error_message_required_field)
            PhoneValidator(phoneNumber).not() -> getString(Res.string.core_ui_error_message_invalid_phone_number)
            else -> null
        },
        passwordError = when {
            RequiredValidator(password).not() -> getString(Res.string.core_ui_error_message_required_field)
            else -> null
        }
    )

private val LoginUiState.containsError get() = phoneNumberError != null || passwordError != null

internal data class LoginUiState(
    val isLoading: Boolean = false,
    val phoneNumber: String = "",
    val phoneNumberError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)

internal sealed interface LoginEvent {
    data object Login : LoginEvent
    data class PhoneNumberChanged(val phoneNumber: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
}

internal sealed interface LoginUiEvent : MessagingUiEvent {
    data object NavigateToDashboard : LoginUiEvent
}
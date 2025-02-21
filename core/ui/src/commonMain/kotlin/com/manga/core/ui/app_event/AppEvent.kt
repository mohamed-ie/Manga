package com.manga.core.ui.app_event

import androidx.compose.material3.SnackbarDuration
import com.manga.core.ui.UiText

sealed class AppEvent(open val message: UiText) {
    data class ShowSnackbar(
        override val message: UiText,
        val actionLabel: UiText? = null,
        val action: (() -> Unit)? = null,
        val duration: SnackbarDuration? = null,
        val withDismissAction: Boolean = false
    ) : AppEvent(message = message)

    data class Error(
        val cancelable: Boolean,
        override val message: UiText,
        val title: UiText?,
        val actionLabel: UiText? = null,
        val action: (() -> Unit)? = null
    ) : AppEvent(message = message)

    data class Info(
        val sticky: Boolean,
        override val message: UiText,
        val actionLabel: UiText? = null,
        val action: (() -> Unit)? = null
    ) : AppEvent(message = message)

    data class Success(
        override val message: UiText,
        val title: UiText?,
    ) : AppEvent(message = message)
}
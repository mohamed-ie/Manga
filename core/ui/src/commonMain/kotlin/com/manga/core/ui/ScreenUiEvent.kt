package com.manga.core.ui

import com.manga.core.ui.UiText

object ScreenUiEvent {
    interface ShowSnackbarAction {
        val message: UiText
        val actionLabel: UiText?
        val action: (() -> Unit)?
    }

    interface ShowToast {
        val message: UiText
    }
}
package com.manga.core.ui.messaging

import androidx.compose.material3.SnackbarDuration

interface MessagingEvent

data class ShowMessage(val message: Message) : MessagingEvent

sealed class Message(open val message: String) {
    data class Snackbar(
        override val message: String,
        val actionLabel: String? = null,
        val action: (() -> Unit)? = null,
        val duration: SnackbarDuration? = null,
        val withDismissAction: Boolean = false
    ) : Message(message = message)

    data class Error(
        val cancelable: Boolean,
        override val message: String,
        val title: String?,
        val actionLabel: String? = null,
        val action: (() -> Unit)? = null
    ) : Message(message = message)

    data class Info(
        val sticky: Boolean,
        override val message: String,
        val actionLabel: String? = null,
        val action: (() -> Unit)? = null
    ) : Message(message = message)

    data class Success(
        override val message: String,
        val title: String?,
    ) : Message(message = message)
}
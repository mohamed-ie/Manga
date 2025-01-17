package com.manga.core.ui.messaging

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.manga.core.ui.dialog.ErrorAlertDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class Messaging internal constructor(
    val snackbarHostState: SnackbarHostState,
    val scope: CoroutineScope
) {
    var info by mutableStateOf<Message.Info?>(null)
        private set

    var error by mutableStateOf<Message.Error?>(null)
        private set

    var success by mutableStateOf<Message.Success?>(null)
        private set

    fun handle(event: MessagingEvent) {
        when (event) {
            is ShowMessage ->
                when (event.message) {
                    is Message.Error -> error = event.message
                    is Message.Info -> info = event.message
                    is Message.Success -> success = event.message
                    is Message.Snackbar -> showSnackbar(event.message)
                }

            else -> Unit
        }
    }

    private fun showSnackbar(message: Message.Snackbar) {
        scope.launch {
            snackbarHostState.showSnackbar(
                message = message.message,
                actionLabel = message.actionLabel,
                withDismissAction = message.withDismissAction
            )
        }
    }

    fun hide(message: Message) {
        when (message) {
            is Message.Error -> error = null
            is Message.Info -> info = null
            is Message.Success -> success = null
            else -> Unit
        }
    }
}

val LocalMessaging = compositionLocalOf<Messaging?> { null }

@Composable
fun ProvideMessaging(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable () -> Unit
) {
    val messaging = remember { Messaging(snackbarHostState, scope) }
    val error by remember { derivedStateOf { messaging.error } }

    CompositionLocalProvider(LocalMessaging provides messaging) {
        content()
    }

    error?.let { message ->
        ErrorAlertDialog(
            message = message.message,
            title = message.title,
            onDismiss = { messaging.hide(message) }
        )
    }
}
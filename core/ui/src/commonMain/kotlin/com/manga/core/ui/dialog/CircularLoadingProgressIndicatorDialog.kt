package com.manga.core.ui.dialog

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CircularLoadingProgressIndicatorDialog(
    visible: Boolean,
    modifier: Modifier = Modifier,
    cancelable: Boolean = true,
    onDismiss: () -> Unit = {}
) {
    if (visible)
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(dismissOnClickOutside = cancelable)
        ) {
            CircularProgressIndicator(modifier = modifier)
        }
}

package com.manga.core.ui.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_action_dismiss
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ErrorAlertDialog(
    modifier: Modifier = Modifier,
    title: String? = null,
    message: String,
    confirmActionText: String = stringResource(Res.string.core_ui_action_dismiss),
    onDismiss: () -> Unit
) = AlertDialog(
    modifier = modifier,
    title = title?.let { { Text(text = title) } },
    text = { Text(text = message) },
    confirmButton = {
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onDismiss
        ) {
            Text(text = confirmActionText)
        }
    },
    onDismissRequest = onDismiss
)

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        ErrorAlertDialog(
            title = "Error",
            message = "An error occurred while trying to load the content.",
            onDismiss = {}
        )
    }
}
package com.manga.feature.more.more

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.manga.core.design_system.icons.MangaIcons
import com.manga.core.design_system.theme.MangaTheme
import kotlinx.collections.immutable.persistentListOf
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_action_retry
import manga.core.ui.generated.resources.core_ui_text_guest
import manga.core.ui.generated.resources.core_ui_text_language
import manga.core.ui.generated.resources.core_ui_text_logout
import manga.core.ui.generated.resources.core_ui_text_manga_settings
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

private val options = persistentListOf(
    Res.string.core_ui_text_language to MangaIcons.More.Language,
    Res.string.core_ui_text_manga_settings to MangaIcons.More.MangaSettings
)

@Composable
internal fun MoreRoute(
    navigateToAccount: () -> Unit,
    navigateToMangaSettings: () -> Unit,
    viewModel: MoreViewModel = koinViewModel()
) {
    MoreScreen()
}

@Composable
internal fun MoreScreen(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxSize()) {
        ListItem(
            headlineContent = {
                Text(stringResource(Res.string.core_ui_text_guest))
            },
            leadingContent = {
                Icon(imageVector = MangaIcons.More.Account, contentDescription = null)
            }
        )

        options.forEach { (res, icon) ->
            ListItem(
                headlineContent = { Text(text = stringResource(res)) },
                trailingContent = { Icon(imageVector = icon, contentDescription = null) }
            )
        }

        Spacer(Modifier.weight(1f))

        ListItem(
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                headlineColor = MaterialTheme.colorScheme.onErrorContainer
            ),
            headlineContent = { Text(text = stringResource(Res.string.core_ui_text_logout)) },
            trailingContent = { Icon(imageVector = MangaIcons.More.Logout, contentDescription = null) }
        )
    }
}

@Composable
fun MoreListItem(
    modifier: Modifier = Modifier,
    headlineText: String,
    trailingIcon: ImageVector
) {

}

@Preview
@Composable
fun PreviewMoreScreen() {
    MangaTheme {
        MoreScreen()
    }
}
package com.manga.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.manga.app.navigation.MangaNavHost
import com.manga.app.navigation.TopLevelDestination
import com.manga.core.ui.messaging.ProvideMessaging
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_text_back_online
import manga.core.ui.generated.resources.core_ui_text_no_internet_connection
import org.jetbrains.compose.resources.stringResource

@Composable
fun MangaApp(
    appState: MangaAppState
) {
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Column(Modifier.consumeWindowInsets(NavigationBarDefaults.windowInsets)) {
                BottomNavigation(appState)
                InternetConnectionStatus(isOffline = isOffline)
                Spacer(modifier = Modifier.padding(NavigationBarDefaults.windowInsets.asPaddingValues()))
            }
        }
    ) { innerPadding ->
        ProvideMessaging(snackbarHostState = snackbarHostState) {
            MangaNavHost(
                modifier = Modifier.consumeWindowInsets(innerPadding).padding(innerPadding),
                appState = appState
            )
        }
    }
}

@Composable
private fun InternetConnectionStatus(modifier: Modifier = Modifier, isOffline: Boolean) =
    AnimatedVisibility(
        modifier = modifier,
        visible = isOffline,
        enter = expandVertically(expandFrom = Alignment.Top),
        exit = shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(delayMillis = 1000)
        )
    ) {

        val color by animateColorAsState(
            targetValue = if (isOffline) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer,
            label = "internet container color"
        )

        val contentColor by animateColorAsState(
            targetValue = if (isOffline) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer,
            label = "internet content color"
        )

        Surface(
            color = color,
            contentColor = contentColor
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = stringResource(if (isOffline) Res.string.core_ui_text_no_internet_connection else Res.string.core_ui_text_back_online),
                textAlign = TextAlign.Center
            )
        }
    }

@Composable
private fun BottomNavigation(appState: MangaAppState) {
    val shouldShowBottomBar by appState.shouldShowBottomBar
    AnimatedVisibility(
        visible = shouldShowBottomBar,
        enter = expandVertically(expandFrom = Alignment.Bottom),
        exit = shrinkVertically(shrinkTowards = Alignment.Bottom)
    ) {
        NavigationBar {
            TopLevelDestination.entries.forEach { destination ->
                val selected =
                    appState.currentDestination?.isTopLevelDestinationInHierarchy(destination) == true
                NavigationBarItem(
                    selected = selected,
                    label = { Text(stringResource(destination.labelResource)) },
                    icon = {
                        Crossfade(selected) {
                            if (it)
                                Icon(
                                    imageVector = destination.selectedIcon,
                                    contentDescription = null
                                )
                            else Icon(
                                imageVector = destination.unselectedIcon,
                                contentDescription = null
                            )
                        }
                    },
                    onClick = { appState.navigateToTopLevelDestination(destination) }
                )
            }
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any { it.hasRoute(destination.route::class) } ?: false

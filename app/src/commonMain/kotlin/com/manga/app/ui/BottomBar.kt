package com.manga.app.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.manga.app.navigation.TopLevelDestination
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomBar(appState: MangaAppState) {
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()

    Column(Modifier.background(NavigationBarDefaults.containerColor).consumeWindowInsets(NavigationBarDefaults.windowInsets)) {
        BottomNavigation(appState)
        NetworkStatusIndicator(isOffline = isOffline)
        Spacer(modifier = Modifier.padding(NavigationBarDefaults.windowInsets.asPaddingValues()))
    }
}

@Composable
fun BottomNavigation(appState: MangaAppState) {
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
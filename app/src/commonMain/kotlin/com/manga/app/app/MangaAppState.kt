package com.manga.app.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.manga.app.app.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import com.manga.core.data.utils.NetworkMonitor

@Composable
fun rememberMangaAppState(
    networkMonitor: NetworkMonitor,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
) = remember(
    navController,
    coroutineScope,
    navController
) {
    MangaAppState(
        navController = navController,
        networkMonitor = networkMonitor,
        coroutineScope = coroutineScope
    )
}

@Stable
class MangaAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    networkMonitor: NetworkMonitor
) {
    fun navigateToTopLevelDestination(destination: TopLevelDestination) =
        navController.navigate(destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }


    val currentDestination
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val startDestination
        get() = navController.graph.findStartDestination()

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    private val _isInTopLevelStartDestination = navController.currentBackStackEntryFlow
        .map { backStackEntry ->
            backStackEntry.destination.parent?.startDestinationRoute == backStackEntry.destination.route
                    && TopLevelDestination.entries.any { it.route == backStackEntry.destination.parent?.route }
        }.stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    val isInTopLevelStartDestination: Boolean
        @Composable
        get() = _isInTopLevelStartDestination.collectAsStateWithLifecycle().value

}
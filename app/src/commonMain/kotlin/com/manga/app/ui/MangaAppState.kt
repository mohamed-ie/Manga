package com.manga.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.compose.utils.app_event.AppEventInvoker
import com.manga.app.navigation.TopLevelDestination
import com.manga.core.data.utils.NetworkMonitor
import com.manga.core.data.utils.TimeZoneMonitor
import com.manga.core.ui.locals.BottomBarState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.datetime.TimeZone

@Composable
fun rememberMangaAppState(
    networkMonitor: NetworkMonitor,
    timeZoneMonitor: TimeZoneMonitor,
    appEventInvoker: AppEventInvoker,
    bottomBarState: BottomBarState = BottomBarState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController()
) = remember(
    coroutineScope,
    timeZoneMonitor,
    bottomBarState,
    navController,
    networkMonitor
) {
    MangaAppState(
        navController = navController,
        networkMonitor = networkMonitor,
        coroutineScope = coroutineScope,
        appEventInvoker = appEventInvoker,
        bottomBarState = bottomBarState,
        timeZoneMonitor = timeZoneMonitor
    )
}

@Stable
class MangaAppState(
    val navController: NavHostController,
    val coroutineScope: CoroutineScope,
    val appEventInvoker: AppEventInvoker,
    val bottomBarState: BottomBarState,
    timeZoneMonitor: TimeZoneMonitor,
    networkMonitor: NetworkMonitor,
) {

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false,
        )

    val currentTimeZone = timeZoneMonitor.currentTimeZone
        .stateIn(
            coroutineScope,
            SharingStarted.WhileSubscribed(5_000),
            TimeZone.currentSystemDefault(),
        )

    private val forceHideBottomBarState = MutableStateFlow(false)

    private val shouldShowBottomBarState = navController.currentBackStackEntryFlow
        .mapLatest { navBackStackEntry ->
            TopLevelDestination.entries.any { destination ->
                navBackStackEntry.destination.hierarchy.any { it.hasRoute(destination.route::class) } &&
                        navBackStackEntry.destination.parent?.startDestinationId == navBackStackEntry.destination.id
            }
        }
        .combine(forceHideBottomBarState.map(Boolean::not), Boolean::and)
        .combine(snapshotFlow { bottomBarState.visible }, Boolean::and)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = false
        )

    val shouldShowBottomBar
        @Composable
        get() = shouldShowBottomBarState.collectAsStateWithLifecycle()

    val currentDestination
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    fun hideBottomBar(hide: Boolean) = forceHideBottomBarState.update { hide }

    fun navigateToTopLevelDestination(route: Any) {
        navController.navigate(route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
//            popUpTo((navController.graph[BottomBarNavigation] as NavGraph).findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }

    fun navigateToTopLevelDestination(destination: TopLevelDestination) =
        navigateToTopLevelDestination(destination.route)
}
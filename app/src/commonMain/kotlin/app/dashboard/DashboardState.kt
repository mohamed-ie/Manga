package app.dashboard

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberDashboardState(
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed)
) = remember(
    navController,
    drawerState
) {
    DashboardState(navController = navController, drawerState = drawerState)
}

@Stable
class DashboardState(
    val navController: NavHostController,
    val drawerState: DrawerState
) {
    val currentDestination
        @Composable
        get() = navController.currentBackStackEntryAsState().value?.destination

    val startDestination
        get() = navController.graph.findStartDestination()
}
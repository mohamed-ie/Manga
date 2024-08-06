package app.dashboard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.dashboard.DashboardState

const val DASHBOARD_NAV_HOST_ROUTE = "dashboard_nav_host_route"

@Composable
fun DashboardNavHost(
    modifier: Modifier = Modifier,
    dashboardState: DashboardState
) {
//    NavHost(
//        modifier = modifier,
//        startDestination = TopLevelDestination.RECENT_UPDATED.route,
//        route = DASHBOARD_NAV_HOST_ROUTE,
//        navController = dashboardState.navController
//    ) {
//        mangaNavigation(navigateToMangaDetails = dashboardState.navController::navigateToMangaDetails)
//        mangaDetailsScreen(navigateToChapter = dashboardState.navController::navigateToChapter)
//        chapterScreen()
//    }
}
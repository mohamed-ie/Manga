package com.manga.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.manga.core.model.manga.MinManga
import com.manga.feature.home.HomeRoute

private const val HOME_ROUTE = "home_route"
const val HOME_NAVIGATION_ROUTE = "home_navigation_route"

fun NavGraphBuilder.homeScreen(
    navigateToChapter: (String) -> Unit,
    navigateToManga: (MinManga) -> Unit,
    navigateToLatestUpdated: () -> Unit,
) = navigation(
    route = HOME_NAVIGATION_ROUTE,
    startDestination = HOME_ROUTE
) {
    composable(HOME_ROUTE) {
        HomeRoute(
            navigateToChapter = navigateToChapter,
            navigateToManga = navigateToManga,
            navigateToLatestUpdated = navigateToLatestUpdated,
        )
    }
}
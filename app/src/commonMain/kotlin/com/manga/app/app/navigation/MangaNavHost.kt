package com.manga.app.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.manga.app.app.MangaAppState
import com.manga.feature.home.navigation.homeScreen

const val MANGA_NAV_HOST_ROUTE = "manga_nav_host_route"

@Composable
fun MangaNavHost(
    modifier: Modifier = Modifier,
    appState: MangaAppState
) {
    NavHost(
        modifier = modifier,
        startDestination = TopLevelDestination.HOME.route,
        route = MANGA_NAV_HOST_ROUTE,
        navController = appState.navController
    ) {
        homeScreen()
    }
}
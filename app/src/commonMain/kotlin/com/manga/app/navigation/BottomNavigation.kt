package com.manga.app.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.manga.feature.explore.navigation.ExploreNavigation
import com.manga.feature.explore.navigation.exploreScreen
import com.manga.feature.library.navigation.libraryScreen
import com.manga.feature.more.navigation.moreScreen
import kotlinx.serialization.Serializable

@Serializable
object BottomNavigation

fun NavController.navigateToBottomNavigation(navOptions: NavOptions? = null) =
    navigate(BottomNavigation, navOptions)

fun NavGraphBuilder.bottomNavigation() =
    navigation<BottomNavigation>(startDestination = ExploreNavigation::class) {
        libraryScreen()
        moreScreen()
        exploreScreen(
            navigateToManga = {},
            navigateToChapter = { /*appState.navController::navigateToChapter*/ }
        )
    }
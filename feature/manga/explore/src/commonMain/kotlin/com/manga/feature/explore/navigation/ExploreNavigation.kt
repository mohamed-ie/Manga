package com.manga.feature.explore.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.manga.feature.explore.ExploreRoute
import kotlinx.serialization.Serializable

@Serializable
data object ExploreNavigation

@Serializable
data object Explore

fun NavController.navigateToExplore(navOptions: NavOptions? = null) =
    navigate(ExploreNavigation, navOptions)

fun NavGraphBuilder.exploreScreen(
    navigateToManga: (String) -> Unit,
    navigateToChapter: (String) -> Unit
) = navigation<ExploreNavigation>(startDestination = Explore::class) {
    composable<Explore> {
        ExploreRoute(
            navigateToManga = navigateToManga,
            navigateToChapter = navigateToChapter
        )
    }
}
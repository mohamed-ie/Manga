package com.manga.feature.latest_updated.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.manga.feature.latest_updated.LatestUpdatedRoute

private const val LATEST_UPDATED_ROUTE = "latest_updated_route"

fun NavController.navigateToLatestUpdated(navOptions: NavOptions? = null) =
    navigate(LATEST_UPDATED_ROUTE, navOptions)

fun NavGraphBuilder.latestUpdatedScreen(
    navigateToManga: (String) -> Unit,
    navigateToChapter: (String) -> Unit
) = composable(LATEST_UPDATED_ROUTE) {
    LatestUpdatedRoute(
        navigateToManga = navigateToManga,
        navigateToChapter = navigateToChapter
    )
}
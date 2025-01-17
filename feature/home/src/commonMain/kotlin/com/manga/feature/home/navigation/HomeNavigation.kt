package com.manga.feature.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.manga.core.model.manga_dex.manga.MinManga
import com.manga.feature.home.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeNavigation

@Serializable
internal data object Home

fun NavGraphBuilder.homeScreen(
    navigateToChapter: (String) -> Unit,
    navigateToManga: (MinManga) -> Unit,
    navigateToLatestUpdated: () -> Unit,
) = navigation<HomeNavigation>(startDestination = Home::class) {
    composable<Home> {
        HomeRoute(
            navigateToChapter = navigateToChapter,
            navigateToManga = navigateToManga,
            navigateToLatestUpdated = navigateToLatestUpdated,
        )
    }
}
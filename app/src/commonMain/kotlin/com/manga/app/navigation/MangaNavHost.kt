package com.manga.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.manga.app.ui.MangaAppState
import com.manga.feature.chapter.navigation.chapterScreen
import com.manga.feature.chapter.navigation.navigateToChapter
import com.manga.feature.home.navigation.homeScreen
import com.manga.feature.latest_updated.navigation.latestUpdatedScreen
import com.manga.feature.latest_updated.navigation.navigateToLatestUpdated
import com.manga.feature.more.more.navigation.moreScreen
import kotlinx.serialization.Serializable

@Serializable
data object MangaNavHost

@Composable
fun MangaNavHost(
    modifier: Modifier = Modifier,
    appState: MangaAppState
) {
    NavHost(
        modifier = modifier,
        startDestination = TopLevelDestination.HOME.route,
        route = MangaNavHost::class,
        navController = appState.navController
    ) {
        homeScreen(
            navigateToManga = {},
            navigateToChapter = appState.navController::navigateToChapter,
            navigateToLatestUpdated = appState.navController::navigateToLatestUpdated
        )

        latestUpdatedScreen(
            navigateToManga = {},
            navigateToChapter = appState.navController::navigateToChapter
        )

        chapterScreen()

        moreScreen(
            navigateToAccount = {},
            navigateToMangaSettings = {}
        )
    }
}
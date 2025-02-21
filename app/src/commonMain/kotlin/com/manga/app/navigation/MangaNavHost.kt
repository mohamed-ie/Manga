package com.manga.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.manga.app.ui.MangaAppState
import com.manga.feature.onboarding.navigation.Onboarding
import com.manga.feature.onboarding.navigation.onboardingScreen
import kotlinx.serialization.Serializable

@Serializable
data object MangaNavHost

@Composable
fun MangaNavHost(
    modifier: Modifier = Modifier,
    appState: MangaAppState
) {
    val navController = appState.navController

    NavHost(
        modifier = modifier,
        startDestination = Onboarding::class,
        route = MangaNavHost::class,
        navController = navController
    ) {
        onboardingScreen(navigateToMainScreen = navController::navigateToBottomNavigation)

        bottomNavigation()
    }
}
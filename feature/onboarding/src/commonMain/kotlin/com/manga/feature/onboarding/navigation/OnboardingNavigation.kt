package com.manga.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.manga.feature.onboarding.OnboardingRoute
import kotlinx.serialization.Serializable
@Serializable
data object Onboarding
fun NavGraphBuilder.onboardingScreen(
    navigateToMainScreen: () -> Unit,
) = composable<Onboarding> {
    OnboardingRoute(
        navigateToMainScreen = navigateToMainScreen
    )
}
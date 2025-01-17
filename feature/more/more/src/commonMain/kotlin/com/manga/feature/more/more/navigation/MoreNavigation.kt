package com.manga.feature.more.more.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.manga.feature.more.more.MoreRoute
import kotlinx.serialization.Serializable

@Serializable
data object MoreNavigation

@Serializable
data object More

fun NavGraphBuilder.moreScreen(
    navigateToAccount: () -> Unit,
    navigateToMangaSettings: () -> Unit,
    nestedGraph: NavGraphBuilder.() -> Unit = {}
) = navigation<MoreNavigation>(
    startDestination = More::class
) {
    composable<More> {
        MoreRoute(
            navigateToAccount=navigateToAccount,
            navigateToMangaSettings=navigateToMangaSettings
        )
    }
    
    nestedGraph()
}
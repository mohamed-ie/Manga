package com.manga.feature.more.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.manga.feature.more.MoreRoute
import kotlinx.serialization.Serializable

@Serializable
data object MoreNavigation

@Serializable
data object More

fun NavController.navigateToMore(navOptions: NavOptions? = null) =
    navigate(MoreNavigation, navOptions)

fun NavGraphBuilder.moreScreen() = navigation<MoreNavigation>(startDestination = More::class) {
    composable<More> {
        MoreRoute()
    }
}
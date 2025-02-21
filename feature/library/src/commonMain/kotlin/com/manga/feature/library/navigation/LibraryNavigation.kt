package com.manga.feature.library.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.manga.feature.library.LibraryRoute
import kotlinx.serialization.Serializable

@Serializable
data object LibraryNavigation

@Serializable
data object Library

fun NavController.navigateToLibrary(navOptions: NavOptions? = null) =
    navigate(LibraryNavigation, navOptions)

fun NavGraphBuilder.libraryScreen() = navigation<LibraryNavigation>(startDestination = Library::class) {
    composable<Library> {
        LibraryRoute()
    }
}
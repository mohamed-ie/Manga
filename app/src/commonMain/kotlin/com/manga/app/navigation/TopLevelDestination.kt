package com.manga.app.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.manga.core.design_system.icons.MangaIcons
import com.manga.feature.explore.navigation.ExploreNavigation
import com.manga.feature.library.navigation.LibraryNavigation
import com.manga.feature.more.navigation.MoreNavigation
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_text_explore
import manga.core.ui.generated.resources.core_ui_text_library
import manga.core.ui.generated.resources.core_ui_text_more
import org.jetbrains.compose.resources.StringResource

enum class TopLevelDestination(
    val route: Any,
    val labelResource: StringResource,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    EXPLORE(
        route = ExploreNavigation,
        labelResource = Res.string.core_ui_text_explore,
        selectedIcon = MangaIcons.NavigationBar.exploreSelected,
        unselectedIcon = MangaIcons.NavigationBar.exploreUnselected
    ),
    LIBRARY(
        route = LibraryNavigation,
        labelResource = Res.string.core_ui_text_library,
        selectedIcon = MangaIcons.NavigationBar.librarySelected,
        unselectedIcon = MangaIcons.NavigationBar.libraryUnselected
    ),
    MORE(
        route = MoreNavigation,
        labelResource = Res.string.core_ui_text_more,
        selectedIcon = MangaIcons.NavigationBar.moreSelected,
        unselectedIcon = MangaIcons.NavigationBar.moreUnselected
    )
}
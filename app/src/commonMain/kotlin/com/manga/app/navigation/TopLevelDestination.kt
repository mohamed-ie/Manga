package com.manga.app.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.manga.core.design_system.icons.MangaIcons
import com.manga.feature.home.navigation.HomeNavigation
import com.manga.feature.more.more.navigation.MoreNavigation
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_text_explore
import manga.core.ui.generated.resources.core_ui_text_home
import manga.core.ui.generated.resources.core_ui_text_library
import manga.core.ui.generated.resources.core_ui_text_more
import org.jetbrains.compose.resources.StringResource

enum class TopLevelDestination(
    val route: Any,
    val labelResource: StringResource,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    HOME(
        route = HomeNavigation,
        labelResource = Res.string.core_ui_text_home,
        selectedIcon = MangaIcons.NavigationBar.homeSelected,
        unselectedIcon = MangaIcons.NavigationBar.homeUnselected
    ),
    EXPLORER(
        route = "",
        labelResource = Res.string.core_ui_text_explore,
        selectedIcon = MangaIcons.NavigationBar.exploreSelected,
        unselectedIcon = MangaIcons.NavigationBar.exploreUnselected
    ),
    LIBRARY(
        route = "",
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
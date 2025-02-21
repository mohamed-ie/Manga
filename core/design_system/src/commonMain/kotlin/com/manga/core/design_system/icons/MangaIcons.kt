package com.manga.core.design_system.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.More
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.twotone.More
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Cyclone
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material.icons.twotone.AutoMode
import androidx.compose.material.icons.twotone.Ballot
import androidx.compose.material.icons.twotone.CollectionsBookmark
import androidx.compose.material.icons.twotone.Cyclone
import androidx.compose.material.icons.twotone.DarkMode
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Language
import androidx.compose.material.icons.twotone.LightMode
import androidx.compose.material.icons.twotone.PowerOff
import androidx.compose.material.icons.twotone.PowerSettingsNew
import androidx.compose.material.icons.twotone.Tune

object MangaIcons {
    object NavigationBar {
        val homeSelected = Icons.Filled.Home
        val homeUnselected = Icons.TwoTone.Home
        val exploreSelected = Icons.Filled.Cyclone
        val exploreUnselected = Icons.TwoTone.Cyclone
        val librarySelected = Icons.Filled.CollectionsBookmark
        val libraryUnselected = Icons.TwoTone.CollectionsBookmark
        val moreSelected = Icons.Filled.Tune
        val moreUnselected = Icons.TwoTone.Tune
    }

    object Common {
        val viewMore = Icons.AutoMirrored.Rounded.KeyboardArrowRight
        val follow = Icons.Rounded.Favorite
        val ratingStarFill = Icons.Rounded.Star
        val scrollToTop = Icons.Rounded.KeyboardArrowUp
    }

    object More{
        val Account = Icons.TwoTone.AccountCircle
        val Language = Icons.TwoTone.Language
        val LightTheme = Icons.TwoTone.LightMode
        val DarkMode = Icons.TwoTone.DarkMode
        val AutoMode = Icons.TwoTone.AutoMode
        val MangaSettings = Icons.TwoTone.Ballot
        val Logout = Icons.TwoTone.PowerSettingsNew
    }
}
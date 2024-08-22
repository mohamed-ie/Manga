package com.manga.core.design_system.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.More
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.twotone.More
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Cyclone
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material.icons.twotone.CollectionsBookmark
import androidx.compose.material.icons.twotone.Cyclone
import androidx.compose.material.icons.twotone.Home

object MangaIcons {
    object NavigationBar {
        val homeSelected = Icons.Filled.Home
        val homeUnselected = Icons.TwoTone.Home
        val exploreSelected = Icons.Filled.Cyclone
        val exploreUnselected = Icons.TwoTone.Cyclone
        val librarySelected = Icons.Filled.CollectionsBookmark
        val libraryUnselected = Icons.TwoTone.CollectionsBookmark
        val moreSelected = Icons.AutoMirrored.Filled.More
        val moreUnselected = Icons.AutoMirrored.TwoTone.More
    }

    object Common {
        val viewMore = Icons.AutoMirrored.Rounded.KeyboardArrowRight
        val follow = Icons.Rounded.Favorite
        val ratingStarFill = Icons.Rounded.Star
        val ratingStarOutline = Icons.Rounded.StarOutline
    }
}
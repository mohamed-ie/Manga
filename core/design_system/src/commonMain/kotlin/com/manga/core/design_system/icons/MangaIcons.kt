package com.manga.core.design_system.icons

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.More
import androidx.compose.material.icons.automirrored.twotone.More
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Cyclone
import androidx.compose.material.icons.filled.Home
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
}
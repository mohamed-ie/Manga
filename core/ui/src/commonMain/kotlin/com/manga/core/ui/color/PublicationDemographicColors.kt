package com.manga.core.ui.color

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.manga.core.model.manga.MangaDexPublicationDemographic

data class PublicationDemographicColors(
    val shounenColor: Color = Color(0xFF003366), // Dark Blue for text
    val shounenContainerColor: Color = Color(0xFFE3F2FD), // Light Blue for background
    val shoujoColor: Color = Color(0xFFFF69B4), // Medium Pink for text
    val shoujoContainerColor: Color = Color(0xFFFFEBEE), // Light Pink for background
    val seinenColor: Color = Color(0xFF333333), // Charcoal Gray for text
    val seinenContainerColor: Color = Color(0xFFF5F5F5), // Light Gray for background
    val joseiColor: Color = Color(0xFF6D28D9), // Dark Plum for text
    val joseiContainerColor: Color = Color(0xFFE9D8FD), // Soft Lilac for background
    val isekaiColor: Color = Color(0xFF003366), // Deep Blue for text
    val isekaiContainerColor: Color = Color(0xFFE6E6FA) // Light Lavender for background
)


val LocalPublicationDemographicColor = compositionLocalOf { PublicationDemographicColors() }

fun PublicationDemographicColors.color(mangaDexPublicationDemographic: MangaDexPublicationDemographic) =
    when (mangaDexPublicationDemographic) {
        MangaDexPublicationDemographic.SHOUNEN -> shounenColor
        MangaDexPublicationDemographic.SHOUJO -> shoujoColor
        MangaDexPublicationDemographic.JOSEI -> joseiColor
        MangaDexPublicationDemographic.SEINEN -> seinenColor
        MangaDexPublicationDemographic.NONE -> Color.White
    }

fun PublicationDemographicColors.containerColor(mangaDexPublicationDemographic: MangaDexPublicationDemographic) =
    when (mangaDexPublicationDemographic) {
        MangaDexPublicationDemographic.SHOUNEN -> shounenContainerColor
        MangaDexPublicationDemographic.SHOUJO -> shoujoContainerColor
        MangaDexPublicationDemographic.JOSEI -> joseiContainerColor
        MangaDexPublicationDemographic.SEINEN -> seinenContainerColor
        MangaDexPublicationDemographic.NONE -> Color.DarkGray
    }

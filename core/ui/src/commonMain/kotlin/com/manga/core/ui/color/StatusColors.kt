package com.manga.core.ui.color

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import com.manga.core.model.manga.Manga.Status

data class StatusColors(
    val ongoingColor: Color = Color(0xFF4CAF50), // Green for ongoing status
    val ongoingContainerColor: Color = Color(0xFFC8E6C9), // Light Green for ongoing status container
    val completedColor: Color = Color(0xFF009688), // Teal for completed status
    val completedContainerColor: Color = Color(0xFFB2DFDB), // Light Teal for completed status container
    val hiatusColor: Color = Color(0xFFFFC107), // Amber for hiatus status
    val hiatusContainerColor: Color = Color(0xFFFFECB3), // Light Amber for hiatus status container
    val canceledColor: Color = Color(0xFFDC143C), // Crimson for canceled status
    val canceledContainerColor: Color = Color(0xFFFFCDD2) // Light Coral for canceled status container
)

val LocalStatusColors = compositionLocalOf { StatusColors() }

fun StatusColors.color(status: Status) =
    when (status) {
        Status.ONGOING -> ongoingColor
        Status.COMPLETED -> completedColor
        Status.HIATUS -> hiatusColor
        Status.CANCELLED -> canceledColor
    }

fun StatusColors.containerColor(status: Status) =
    when (status) {
        Status.ONGOING -> ongoingContainerColor
        Status.COMPLETED -> completedContainerColor
        Status.HIATUS -> hiatusContainerColor
        Status.CANCELLED -> canceledContainerColor
    }
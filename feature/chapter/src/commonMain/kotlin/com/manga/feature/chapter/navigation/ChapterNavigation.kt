package com.manga.feature.chapter.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.manga.feature.chapter.ChapterRoute

internal const val ARG_CHAPTER_ID = "chapter_id"
private const val CHAPTER_ROUTE = "chapter_route/{$ARG_CHAPTER_ID}"

fun NavController.navigateToChapter(chapterId:String,navOptions: NavOptions?=null) =
    navigate("chapter_route/$chapterId",navOptions)

fun NavGraphBuilder.chapterScreen() =
    composable(CHAPTER_ROUTE) {
        ChapterRoute()
    }

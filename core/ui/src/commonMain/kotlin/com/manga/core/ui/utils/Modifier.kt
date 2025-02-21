package com.manga.core.ui.utils

import androidx.compose.ui.Modifier

fun Modifier.then(condition: Boolean, modifier: Modifier) =
    then(if (condition) modifier else Modifier)
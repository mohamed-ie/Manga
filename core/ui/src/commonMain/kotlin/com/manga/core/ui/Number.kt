package com.manga.core.ui

import kotlin.math.pow
import kotlin.math.round

val Long.shortHand: String
    get() {
        val rounded = round(div(100f))
        return when {
            this >= 1_000_000 -> "${rounded.div(10_000f)}M"
            this >= 1_000 -> "${rounded.div(10)}K"
            else -> this.toString()
        }
    }

fun Float.roundToFloatingPoint(number: Int = 1) = round(this * 10f.pow(number)).div(10f.pow(number))
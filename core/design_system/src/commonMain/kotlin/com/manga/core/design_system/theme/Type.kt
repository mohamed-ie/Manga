package com.manga.core.design_system.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import manga.core.design_system.generated.resources.Res
import manga.core.design_system.generated.resources.m_plus_rounded_1c_black
import manga.core.design_system.generated.resources.m_plus_rounded_1c_bold
import manga.core.design_system.generated.resources.m_plus_rounded_1c_extra_bold
import manga.core.design_system.generated.resources.m_plus_rounded_1c_light
import manga.core.design_system.generated.resources.m_plus_rounded_1c_medium
import manga.core.design_system.generated.resources.m_plus_rounded_1c_regular
import manga.core.design_system.generated.resources.m_plus_rounded_1c_thin
import org.jetbrains.compose.resources.Font

internal val bodyFontFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.m_plus_rounded_1c_light, FontWeight.Light),
        Font(Res.font.m_plus_rounded_1c_thin, FontWeight.Thin),
        Font(Res.font.m_plus_rounded_1c_regular, FontWeight.Normal),
        Font(Res.font.m_plus_rounded_1c_medium, FontWeight.Medium),
        Font(Res.font.m_plus_rounded_1c_bold, FontWeight.Bold),
        Font(Res.font.m_plus_rounded_1c_extra_bold, FontWeight.ExtraBold),
        Font(Res.font.m_plus_rounded_1c_black, FontWeight.Bold)
    )

internal val displayFontFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.m_plus_rounded_1c_light, FontWeight.Light),
        Font(Res.font.m_plus_rounded_1c_thin, FontWeight.Thin),
        Font(Res.font.m_plus_rounded_1c_regular, FontWeight.Normal),
        Font(Res.font.m_plus_rounded_1c_medium, FontWeight.Medium),
        Font(Res.font.m_plus_rounded_1c_bold, FontWeight.Bold),
        Font(Res.font.m_plus_rounded_1c_extra_bold, FontWeight.ExtraBold),
        Font(Res.font.m_plus_rounded_1c_black, FontWeight.Bold)
    )

internal val baseline = Typography()

internal val mangaTypography
    @Composable
    get() = Typography(
        displayLarge = baseline.displayLarge.copy(fontFamily = displayFontFamily),
        displayMedium = baseline.displayMedium.copy(fontFamily = displayFontFamily),
        displaySmall = baseline.displaySmall.copy(fontFamily = displayFontFamily),
        headlineLarge = baseline.headlineLarge.copy(fontFamily = displayFontFamily),
        headlineMedium = baseline.headlineMedium.copy(fontFamily = displayFontFamily),
        headlineSmall = baseline.headlineSmall.copy(fontFamily = displayFontFamily),
        titleLarge = baseline.titleLarge.copy(fontFamily = displayFontFamily),
        titleMedium = baseline.titleMedium.copy(fontFamily = displayFontFamily),
        titleSmall = baseline.titleSmall.copy(fontFamily = displayFontFamily),
        bodyLarge = baseline.bodyLarge.copy(fontFamily = bodyFontFamily),
        bodyMedium = baseline.bodyMedium.copy(fontFamily = bodyFontFamily),
        bodySmall = baseline.bodySmall.copy(fontFamily = bodyFontFamily),
        labelLarge = baseline.labelLarge.copy(fontFamily = bodyFontFamily),
        labelMedium = baseline.labelMedium.copy(fontFamily = bodyFontFamily),
        labelSmall = baseline.labelSmall.copy(fontFamily = bodyFontFamily),
    )


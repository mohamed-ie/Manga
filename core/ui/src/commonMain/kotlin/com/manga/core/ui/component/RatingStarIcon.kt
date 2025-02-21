package com.manga.core.ui.component

import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.unit.LayoutDirection
import com.manga.core.design_system.icons.MangaIcons
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_content_description_rating
import org.jetbrains.compose.resources.stringResource

@Composable
fun RatingStarIcon(
    modifier: Modifier = Modifier,
    rating: Float,
    tint: Color = LocalContentColor.current,
    contentDescription: String = stringResource(Res.string.core_ui_content_description_rating)
) = Icon(
    modifier = modifier.drawWithContent {
        if (layoutDirection == LayoutDirection.Rtl)
            clipRect(left = size.width.times(rating.div(10f))) {
                this@drawWithContent.drawContent()
            }
        else
            clipRect(right = size.width.times(rating.div(10f))) {
                this@drawWithContent.drawContent()
            }
    },
    tint = tint,
    imageVector = MangaIcons.Common.ratingStarFill,
    contentDescription = contentDescription
)
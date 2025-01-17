package com.manga.core.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.manga.core.design_system.icons.MangaIcons
import com.manga.core.model.manga_dex.manga.MangaDexTagGroup
import com.manga.core.model.manga_dex.manga.MinManga
import com.manga.core.model.manga_dex.manga.MinTag
import com.manga.core.ui.component.MangaSubComposeAsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.core_ui_content_description_follows
import manga.core.ui.generated.resources.core_ui_content_description_rating
import org.jetbrains.compose.resources.stringResource
import kotlin.math.absoluteValue
import kotlin.time.Duration.Companion.seconds


@Composable
fun FeaturedMangaHorizontalPager(
    modifier: Modifier = Modifier,
    mangaList: List<MinManga>,
    onMangaClick: (MinManga) -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = Int.MAX_VALUE.div(2)) { Int.MAX_VALUE }
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(isDragged) {
        while (isActive) {
            delay(5.seconds)
            pagerState.animateScrollToPage(
                pagerState.currentPage + 1,
                animationSpec = spring(stiffness = Spring.StiffnessVeryLow)
            )
        }
    }

    HorizontalPager(
        modifier = modifier,
        state = pagerState,
        key = { mangaList[it % mangaList.size].id },
        pageSpacing = 8.dp
    ) { page ->

        FeaturedMangaCard(
            modifier = Modifier
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                    // We animate the alpha, between 50% and 100%
                    val fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    alpha = lerp(
                        start = 0.8f,
                        stop = 1f,
                        fraction = fraction
                    )

                    scaleX = lerp(
                        start = .9f,
                        stop = 1f,
                        fraction = fraction
                    )

                    scaleY = lerp(
                        start = .9f,
                        stop = 1f,
                        fraction = fraction
                    )

                    shape = RoundedCornerShape(
                        androidx.compose.ui.unit.lerp(
                            start = 16.dp,
                            stop = 0.dp,
                            fraction = fraction
                        )
                    )

                    clip = true
                },
            manga = mangaList[page % mangaList.size],
            onClick = { onMangaClick(mangaList[page % mangaList.size]) }
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeaturedMangaCard(
    modifier: Modifier = Modifier,
    manga: MinManga,
    onClick: () -> Unit
) = Surface(
    modifier = modifier.fillMaxSize(),
    contentColor = MaterialTheme.colorScheme.onBackground,
    color = MaterialTheme.colorScheme.background,
    onClick = onClick
) {
    MangaSubComposeAsyncImage(
        modifier = Modifier.fillMaxSize(),
        model = manga.cover,
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )

    Box(
        Modifier.fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background.copy(0.2f),
                        MaterialTheme.colorScheme.background.copy(0.7f),
                        MaterialTheme.colorScheme.background
                    ),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = manga.title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Clip
        )

        Spacer(modifier = Modifier.height(8.dp))

        manga.tags[MangaDexTagGroup.GENRE]?.let { tags ->
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                tags.forEach {
                    FeaturedMangaTag(tag = it)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        manga.statistics?.let { statistics ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = MangaIcons.Common.follow,
                    contentDescription = stringResource(Res.string.core_ui_content_description_follows)
                )

                Spacer(Modifier.width(ButtonDefaults.IconSpacing))

                Text(
                    text = statistics.follows.shortHand,
                    style = MaterialTheme.typography.labelSmall
                )

                Spacer(modifier = Modifier.width(24.dp))

                RatingStarIcon(
                    rating = statistics.rating
                )

                Spacer(Modifier.width(ButtonDefaults.IconSpacing))

                Text(
                    text = statistics.rating.roundToFloatingPoint().toString(),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

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

@Composable
fun FeaturedMangaTag(
    modifier: Modifier = Modifier,
    tag: MinTag,
    onClick: (() -> Unit)? = null
) = Text(
    modifier = modifier
        .clickable(enabled = onClick != null, onClick = { onClick?.invoke() })
        .clip(MaterialTheme.shapes.extraSmall)
        .background(MaterialTheme.colorScheme.surfaceVariant)
        .padding(vertical = 0.dp, horizontal = 8.dp),
    text = tag.name,
    style = MaterialTheme.typography.labelSmall,
    color = MaterialTheme.colorScheme.onSurfaceVariant
)

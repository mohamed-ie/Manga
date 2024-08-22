package com.manga.core.ui.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.manga.core.design_system.theme.MangaTheme
import com.manga.core.model.chapter.MinChapter
import com.manga.core.model.manga.MangaDexPublicationDemographic
import com.manga.core.model.manga.MangaDexStatus
import com.manga.core.model.manga.MinManga
import com.manga.core.ui.RatingStarIcon
import com.manga.core.ui.color.LocalPublicationDemographicColor
import com.manga.core.ui.color.LocalStatusColors
import com.manga.core.ui.color.color
import com.manga.core.ui.color.containerColor
import com.manga.core.ui.component.MangaSubComposeAsyncImage
import com.manga.core.ui.relativeTime
import com.manga.core.ui.shimmer
import kotlinx.datetime.Clock
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.text_chapter_number
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MangaCard(
    modifier: Modifier = Modifier,
    manga: MinManga?,
    showChapter: Boolean,
    onClick: () -> Unit,
    onChapterClick: (MinChapter) -> Unit
) {
    val publicationDemographicColors = LocalPublicationDemographicColor.current
    val statusColors = LocalStatusColors.current

    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(enabled = manga != null, onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(Modifier.weight(1f).fillMaxWidth()) {
            MangaSubComposeAsyncImage(
                modifier = Modifier.fillMaxSize()
                    .clip(shape = MaterialTheme.shapes.small)
                    .shimmer(visible = manga == null),
                model = manga?.cover,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Box(
                Modifier.background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.background.copy(0.2f),
                            MaterialTheme.colorScheme.background.copy(0.7f),
                            MaterialTheme.colorScheme.background
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                ).fillMaxSize()
            )

            manga?.publicationDemographic?.let {
                Row(
                    modifier = Modifier.align(Alignment.BottomStart).padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        color = publicationDemographicColors.containerColor(it),
                        contentColor = publicationDemographicColors.color(it),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            text = it.name.lowercase().replaceFirstChar(Char::uppercase)
                        )
                    }

                    Spacer(Modifier.weight(1f))
                    manga.statistics?.let { statistics ->
                        RatingStarIcon(
                            rating = statistics.rating
                        )
                    }
                }
            }

            manga?.status?.let {
                Surface(
                    modifier = Modifier.align(Alignment.TopEnd).padding(8.dp),
                    color = statusColors.containerColor(it),
                    contentColor = statusColors.color(it),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        text = it.name.lowercase().replaceFirstChar(Char::uppercase)
                    )
                }
            }
        }

        Text(
            modifier = Modifier.fillMaxWidth()
                .shimmer(
                    visible = manga == null,
                    lines = 2,
                    shape = MaterialTheme.shapes.small
                ),
            text = manga?.title ?: "",
            minLines = 2,
            maxLines = 2,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.bodyMedium
        )

        if (showChapter)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = MaterialTheme.shapes.small
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .shimmer(visible = manga == null)
                        .clickable(
                            enabled = manga != null,
                            role = Role.Button,
                            onClick = { manga?.lastChapter?.let { onChapterClick(it) } }
                        )
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.weight(1f).padding(end = 8.dp),
                        text = manga?.lastChapter?.name?.let {
                            stringResource(
                                Res.string.text_chapter_number,
                                it
                            )
                        } ?: "",
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Clip
                    )

                    Text(
                        text = manga?.lastChapter?.readableAt?.relativeTime ?: "",
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Clip
                    )
                }
            }
    }
}

@Preview
@Composable
fun MangaCardPreview() {
    MangaTheme {
        MangaCard(
            modifier = Modifier,
            manga = MinManga(
                id = "",
                title = """"冴えない栞(35)の隣に【魚男】が引っ越して来て、代わり映えのない毎日が激変!! 魚男が時々超絶イケメンに見えるのはなぜ――!? そして栞の恋が動き始める…""",
                cover = null,
                lastChapter = MinChapter(
                    id = "id",
                    name = "Hal Calderon",
                    readableAt = Clock.System.now()
                ),
                status = MangaDexStatus.CANCELLED,
                publicationDemographic = MangaDexPublicationDemographic.SEINEN,
                statistics = null,
                tags = emptyMap(),
                description = """冴えない栞(35)の隣に【魚男】が引っ越して来て、代わり映えのない毎日が激変!! 魚男が時々超絶イケメンに見えるのはなぜ――!? そして栞の恋が動き始める…"""
            ),
            onClick = {},
            showChapter = false,
            onChapterClick = {}
        )
    }
}
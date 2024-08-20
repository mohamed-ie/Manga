package com.manga.core.ui.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.ui.com.manga.core.ui.MangaSubComposeAsyncImage
import core.ui.com.manga.core.ui.color.LocalPublicationDemographicColor
import core.ui.com.manga.core.ui.color.LocalStatusColors
import core.ui.com.manga.core.ui.color.color
import core.ui.com.manga.core.ui.color.containerColor
import core.ui.com.manga.core.ui.relativeTime
import core.ui.com.manga.core.ui.shimmer
import kotlinx.datetime.Clock
import com.manga.core.model.manga.MangaChapter
import com.manga.core.model.manga.MinManga
import com.manga.core.model.manga.Status
import manga.core.ui.generated.resources.Res
import manga.core.ui.generated.resources.text_chapter_number
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.manga.core.design_system.theme.MangaTheme

@Composable
fun MangaCard(
    modifier: Modifier = Modifier,
    manga: MinManga?,
    onClick: () -> Unit,
    onChapterClick: (MangaChapter) -> Unit
) {
    val publicationDemographicColors = LocalPublicationDemographicColor.current
    val statusColors = LocalStatusColors.current
    Column(
        modifier = modifier.clickable(enabled = manga != null, onClick = onClick),
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

            manga?.publicationDemographic?.let {
                Surface(
                    modifier = Modifier.align(Alignment.BottomStart).padding(8.dp),
                    color = publicationDemographicColors.containerColor(it),
                    contentColor = publicationDemographicColors.color(it),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        text = it.name.lowercase().replaceFirstChar { it.uppercase() }
                    )
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
                        text = it.name.lowercase().replaceFirstChar { it.uppercase() }
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
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Surface(
            color = MaterialTheme.colorScheme.surface,
            shape = MaterialTheme.shapes.small
        ) {
            Row(modifier = Modifier.fillMaxWidth()
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
                    text = manga?.updatedAt?.relativeTime ?: "",
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
                title = "title",
                cover = null,
                lastChapter = MangaChapter("100", ""),
                status = Status.CANCELED,
                publicationDemographic = null,
                updatedAt = Clock.System.now()
            ),
            onClick = {},
            onChapterClick = {}
        )
    }
}
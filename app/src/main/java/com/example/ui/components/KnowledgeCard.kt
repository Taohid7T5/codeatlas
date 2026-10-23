package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.KnowledgeEntryWithBookmark
import com.example.ui.theme.AtlasBorder
import com.example.ui.theme.AtlasCyan
import com.example.ui.theme.AtlasGreen
import com.example.ui.theme.AtlasOrange
import com.example.ui.theme.AtlasRed
import com.example.ui.theme.AtlasSurface
import com.example.ui.theme.AtlasSurfaceVariant
import com.example.ui.theme.AtlasTextMuted
import com.example.ui.theme.AtlasTextPrimary
import com.example.ui.theme.AtlasTextSecondary
import com.example.ui.theme.AtlasViolet
import com.example.ui.theme.AtlasYellow
import com.example.ui.theme.CodeBg

@Composable
fun KnowledgeCard(
    entryWithBookmark: KnowledgeEntryWithBookmark,
    onClick: () -> Unit,
    onToggleBookmark: () -> Unit,
    modifier: Modifier = Modifier
) {
    val entry = entryWithBookmark.entry

    val categoryColor = when (entry.contentType) {
        "Keywords" -> AtlasOrange
        "Functions and methods" -> AtlasViolet
        "Errors and debugging" -> AtlasRed
        "Coding questions" -> AtlasYellow
        "Syntax and operators" -> AtlasGreen
        "Algorithms and data structures" -> AtlasCyan
        else -> AtlasCyan
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, AtlasBorder, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag("knowledge_card_${entry.id}"),
        colors = CardDefaults.cardColors(containerColor = AtlasSurface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Top row: Badges and Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Category pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(categoryColor.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = entry.contentType,
                            color = categoryColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Language pill (if specific)
                    if (!entry.languageName.isNullOrBlank()) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(AtlasSurfaceVariant)
                                .border(0.5.dp, AtlasBorder, RoundedCornerShape(6.dp))
                                .padding(horizontal = 7.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = entry.languageName,
                                color = AtlasTextSecondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Difficulty pill
                    val diffColor = when (entry.difficulty) {
                        "Beginner" -> AtlasGreen
                        "Intermediate" -> AtlasYellow
                        else -> AtlasRed
                    }
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(diffColor.copy(alpha = 0.12f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = entry.difficulty,
                            color = diffColor,
                            fontSize = 10.sp
                        )
                    }
                }

                IconButton(
                    onClick = onToggleBookmark,
                    modifier = Modifier
                        .size(32.dp)
                        .testTag("bookmark_btn_${entry.id}")
                ) {
                    Icon(
                        imageVector = if (entryWithBookmark.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = if (entryWithBookmark.isBookmarked) "Bookmarked" else "Bookmark",
                        tint = if (entryWithBookmark.isBookmarked) AtlasYellow else AtlasTextMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Title
            Text(
                text = entry.title,
                style = MaterialTheme.typography.titleMedium,
                color = AtlasTextPrimary,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Definition Preview
            Text(
                text = entry.definition,
                style = MaterialTheme.typography.bodyMedium,
                color = AtlasTextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 20.sp
            )

            // Syntax pattern preview if present
            if (entry.syntax.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(CodeBg)
                        .border(0.5.dp, AtlasBorder, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = entry.syntax.lines().firstOrNull() ?: entry.syntax,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp,
                        color = AtlasCyan,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Footer row: Source and Related
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.OpenInNew,
                        contentDescription = null,
                        tint = AtlasTextMuted,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = entry.sourceName,
                        style = MaterialTheme.typography.labelSmall,
                        color = AtlasTextMuted,
                        fontSize = 11.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = "Explore ➔",
                    style = MaterialTheme.typography.labelSmall,
                    color = AtlasCyan,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

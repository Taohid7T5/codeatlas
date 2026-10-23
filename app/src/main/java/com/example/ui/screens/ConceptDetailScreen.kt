package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CodeSnippetIllustrationCard
import com.example.ui.components.SyntaxCodeBlock
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
import com.example.ui.viewmodel.CodeAtlasViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ConceptDetailScreen(
    viewModel: CodeAtlasViewModel,
    onBack: () -> Unit,
    onNavigateToSearchWithQuery: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBack()
    }

    val entryWithBookmark by viewModel.selectedEntryWithBookmark.collectAsState()
    val context = LocalContext.current

    if (entryWithBookmark == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading concept details...", color = AtlasTextSecondary)
        }
        return
    }

    val entry = entryWithBookmark!!.entry
    val isBookmarked = entryWithBookmark!!.isBookmarked
    val existingNote = entryWithBookmark!!.privateNote

    var noteText by remember(existingNote) { mutableStateOf(existingNote) }
    var noteSavedMessage by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("concept_detail_screen"),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // App Bar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("detail_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = AtlasTextPrimary
                        )
                    }
                    Text(
                        text = "Concept Reference",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AtlasTextPrimary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { viewModel.toggleBookmark(entry.id) },
                        modifier = Modifier.testTag("detail_bookmark_button")
                    ) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "Bookmark",
                            tint = if (isBookmarked) AtlasYellow else AtlasTextSecondary
                        )
                    }

                    if (entry.sourceUrl.isNotBlank()) {
                        IconButton(
                            onClick = {
                                try {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(entry.sourceUrl))
                                    context.startActivity(intent)
                                } catch (_: Exception) {}
                            },
                            modifier = Modifier.testTag("detail_open_doc_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.OpenInNew,
                                contentDescription = "Open official doc",
                                tint = AtlasCyan
                            )
                        }
                    }
                }
            }
        }

        // Title and Metadata Badges
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                // Badges row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(AtlasCyan.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = entry.contentType,
                            color = AtlasCyan,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (!entry.languageName.isNullOrBlank()) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(AtlasViolet.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = entry.languageName,
                                color = AtlasViolet,
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(AtlasSurfaceVariant)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = entry.difficulty,
                            color = AtlasGreen,
                            fontSize = 11.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = entry.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = AtlasTextPrimary,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Definition Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = AtlasSurface),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AtlasBorder)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "OVERVIEW",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtlasTextMuted,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = entry.definition,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AtlasTextPrimary,
                            lineHeight = 22.sp
                        )
                    }
                }
            }
        }

        // Syntax Pattern
        if (entry.syntax.isNotBlank()) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "SYNTAX & USAGE PATTERN",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtlasTextMuted,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(CodeBg)
                            .border(1.dp, AtlasBorder, RoundedCornerShape(10.dp))
                            .padding(14.dp)
                    ) {
                        Text(
                            text = entry.syntax,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 13.sp,
                            color = AtlasCyan,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }

        // Interactive Code Block with syntax highlighting & copy
        if (entry.exampleCode.isNotBlank()) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "VERIFIED CODE EXAMPLE",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtlasTextMuted,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    SyntaxCodeBlock(
                        code = entry.exampleCode,
                        language = entry.languageName ?: "CODE"
                    )
                }
            }
        }

        // Visual Picture of Code (Requested Feature: "Add a different pic of the code as example")
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = "VISUAL CODE INSPECTION",
                    style = MaterialTheme.typography.labelSmall,
                    color = AtlasTextMuted,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                CodeSnippetIllustrationCard(
                    title = "${entry.title} • Live IDE Snapshot",
                    subtitle = "Visual token structure, execution buffer, and AST analysis"
                )
            }
        }

        // Example Explanation
        if (entry.exampleExplanation.isNotBlank()) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "EXPLANATION & ANALYSIS",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtlasTextMuted,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = entry.exampleExplanation,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AtlasTextSecondary,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Common Mistakes & Debugging Tips
        if (entry.commonMistakes.isNotBlank()) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = AtlasRed.copy(alpha = 0.08f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AtlasRed.copy(alpha = 0.3f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.WarningAmber,
                                contentDescription = null,
                                tint = AtlasRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "COMMON PITFALLS & DEBUGGING",
                                style = MaterialTheme.typography.labelSmall,
                                color = AtlasRed,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = entry.commonMistakes,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AtlasTextPrimary,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }

        // Related Concepts Tags
        if (entry.relatedConcepts.isNotBlank()) {
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = "RELATED CONCEPTS",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtlasTextMuted,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        entry.relatedConcepts.split(",").map { it.trim() }.filter { it.isNotEmpty() }.forEach { concept ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(AtlasSurfaceVariant)
                                    .border(1.dp, AtlasBorder, RoundedCornerShape(8.dp))
                                    .clickable { onNavigateToSearchWithQuery(concept) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = concept,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AtlasCyan
                                )
                            }
                        }
                    }
                }
            }
        }

        // Source Attribution Link
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(AtlasSurface)
                    .border(1.dp, AtlasBorder, RoundedCornerShape(10.dp))
                    .clickable {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(entry.sourceUrl))
                            context.startActivity(intent)
                        } catch (_: Exception) {}
                    }
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "OFFICIAL SOURCE",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtlasTextMuted,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = entry.sourceName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AtlasCyan,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Icon(
                    imageVector = Icons.Default.OpenInNew,
                    contentDescription = "Open Source Link",
                    tint = AtlasCyan,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // Private Study Notes Section
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = AtlasSurface),
                border = androidx.compose.foundation.BorderStroke(1.dp, AtlasBorder)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = AtlasViolet,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "PERSONAL STUDY NOTES",
                                style = MaterialTheme.typography.labelSmall,
                                color = AtlasTextPrimary,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        }

                        if (noteSavedMessage) {
                            Text(
                                text = "Saved!",
                                color = AtlasGreen,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = noteText,
                        onValueChange = {
                            noteText = it
                            noteSavedMessage = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .testTag("private_note_input"),
                        placeholder = {
                            Text(
                                text = "Write your private observations, interview tips, or key takeaways...",
                                color = AtlasTextMuted,
                                fontSize = 13.sp
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = AtlasSurfaceVariant,
                            unfocusedContainerColor = AtlasSurfaceVariant,
                            focusedBorderColor = AtlasCyan,
                            unfocusedBorderColor = AtlasBorder,
                            focusedTextColor = AtlasTextPrimary,
                            unfocusedTextColor = AtlasTextPrimary
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            viewModel.savePrivateNote(entry.id, noteText)
                            noteSavedMessage = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AtlasCyan),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .align(Alignment.End)
                            .testTag("save_note_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = null,
                            tint = Color(0xFF0D1117),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Save Note",
                            color = Color(0xFF0D1117),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

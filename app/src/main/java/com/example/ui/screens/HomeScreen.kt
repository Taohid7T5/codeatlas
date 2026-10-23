package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.AutocompleteSuggestion
import com.example.data.model.KnowledgeEntryWithBookmark
import com.example.data.model.LanguageEntity
import com.example.ui.components.CodeSnippetIllustrationCard
import com.example.ui.components.KnowledgeCard
import com.example.ui.components.SearchBarWithAutocomplete
import com.example.ui.theme.AtlasBorder
import com.example.ui.theme.AtlasCyan
import com.example.ui.theme.AtlasGreen
import com.example.ui.theme.AtlasSurface
import com.example.ui.theme.AtlasSurfaceVariant
import com.example.ui.theme.AtlasTextMuted
import com.example.ui.theme.AtlasTextPrimary
import com.example.ui.theme.AtlasTextSecondary
import com.example.ui.theme.AtlasViolet
import com.example.ui.viewmodel.CodeAtlasViewModel

import androidx.compose.material.icons.filled.AutoAwesome

@Composable
fun HomeScreen(
    viewModel: CodeAtlasViewModel,
    onNavigateToSearch: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToLanguages: () -> Unit,
    onNavigateToAi: () -> Unit,
    modifier: Modifier = Modifier
) {
    val query by viewModel.searchQuery.collectAsState()
    val suggestions by viewModel.autocompleteSuggestions.collectAsState()
    val languages by viewModel.languages.collectAsState()
    val trendingEntries by viewModel.trendingEntries.collectAsState()
    val isDarkTheme by viewModel.isDarkTheme.collectAsState()

    val trendingTopics = listOf(
        "loop in python",
        "async/await",
        "recursion",
        "SQL joins",
        "reverse string python",
        "functions in python",
        "TypeError",
        "Rust ownership",
        "binary search",
        "let vs const"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Top CodeAtlas Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_codeatlas_logo),
                        contentDescription = "CodeAtlas Logo",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "CodeAtlas",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Black,
                            color = AtlasTextPrimary,
                            letterSpacing = (-0.5).sp
                        )
                    }
                }

                IconButton(
                    onClick = { viewModel.toggleTheme() },
                    modifier = Modifier.testTag("theme_toggle_button")
                ) {
                    Icon(
                        imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Toggle theme",
                        tint = AtlasCyan
                    )
                }
            }
        }

        // Hero Section & Tagline
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "Every Coding Concept.\nOne Search.",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = AtlasTextPrimary,
                    lineHeight = 36.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Universal programming search engine for keywords, syntax rules, functions, coding questions, and error diagnostics.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AtlasTextSecondary,
                    lineHeight = 22.sp
                )
            }
        }

        // Central Prominent Search Bar with Autocomplete Dropdown
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                SearchBarWithAutocomplete(
                    query = query,
                    onQueryChange = { viewModel.onQueryChange(it) },
                    suggestions = suggestions,
                    onSearch = { searchStr ->
                        viewModel.performSearch(searchStr)
                        onNavigateToSearch()
                    },
                    onSuggestionClick = { suggestion ->
                        if (suggestion.entryId != null) {
                            viewModel.selectEntry(suggestion.entryId)
                            onNavigateToDetail(suggestion.entryId)
                        } else {
                            viewModel.performSearch(suggestion.targetQuery)
                            onNavigateToSearch()
                        }
                    },
                    placeholderText = "Search 'async/await', 'recursion', 'TypeError'..."
                )
            }
        }

        // Ask CodeAtlas AI Prompt Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 2.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(AtlasViolet.copy(alpha = 0.12f))
                    .border(1.dp, AtlasViolet.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                    .clickable { onNavigateToAi() }
                    .padding(horizontal = 14.dp, vertical = 10.dp)
                    .testTag("home_ask_ai_banner")
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = AtlasViolet,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Can't find code or solution?",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = AtlasTextPrimary
                            )
                            Text(
                                text = "Ask CodeAtlas AI for instant code & error diagnostics",
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 11.sp,
                                color = AtlasTextSecondary
                            )
                        }
                    }

                    Text(
                        text = "Ask AI ➔",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtlasViolet,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Trending Search Chips
        item {
            Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = AtlasCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "TRENDING SEARCHES",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtlasTextMuted,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(trendingTopics) { topic ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(AtlasSurfaceVariant)
                                .border(1.dp, AtlasBorder, RoundedCornerShape(20.dp))
                                .clickable {
                                    viewModel.performSearch(topic)
                                    onNavigateToSearch()
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .testTag("trending_chip_$topic")
                        ) {
                            Text(
                                text = topic,
                                style = MaterialTheme.typography.bodySmall,
                                color = AtlasTextPrimary,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }

        // Code Example Visual Asset Feature (Requested: "Add a different pic of the code as example")
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp)
            ) {
                CodeSnippetIllustrationCard(
                    title = "CodeAtlas Visual Engine",
                    subtitle = "IDE token analysis, syntax tree, and algorithmic execution"
                )
            }
        }

        // Programming Languages Shortcuts (17 Languages supported)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = null,
                            tint = AtlasViolet,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "LANGUAGE EXPLORER (17)",
                            style = MaterialTheme.typography.labelSmall,
                            color = AtlasTextMuted,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Text(
                        text = "View All",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtlasCyan,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .clickable(onClick = onNavigateToLanguages)
                            .padding(4.dp)
                            .testTag("view_all_languages_btn")
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(languages) { lang ->
                        LanguageShortcutItem(
                            language = lang,
                            onClick = {
                                viewModel.setLanguageFilter(lang.slug)
                                onNavigateToSearch()
                            }
                        )
                    }
                }
            }
        }

        // Popular Concepts & Knowledge Entries
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = null,
                        tint = AtlasGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "FEATURED CONCEPTS",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtlasTextMuted,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                Text(
                    text = "See All",
                    style = MaterialTheme.typography.labelSmall,
                    color = AtlasCyan,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable(onClick = onNavigateToSearch)
                        .padding(4.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        items(trendingEntries, key = { it.id }) { entry ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 6.dp)
            ) {
                KnowledgeCard(
                    entryWithBookmark = KnowledgeEntryWithBookmark(
                        entry = entry,
                        isBookmarked = false
                    ),
                    onClick = {
                        viewModel.selectEntry(entry.id)
                        onNavigateToDetail(entry.id)
                    },
                    onToggleBookmark = {
                        viewModel.toggleBookmark(entry.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun LanguageShortcutItem(
    language: LanguageEntity,
    onClick: () -> Unit
) {
    val langColor = try {
        Color(android.graphics.Color.parseColor(language.colorHex))
    } catch (_: Exception) {
        AtlasCyan
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(AtlasSurface)
            .border(1.dp, AtlasBorder, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .testTag("lang_shortcut_${language.slug}")
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(langColor)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = language.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = AtlasTextPrimary
            )
        }
    }
}

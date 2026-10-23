package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AutocompleteSuggestion
import com.example.ui.components.KnowledgeCard
import com.example.ui.components.SearchBarWithAutocomplete
import com.example.ui.theme.AtlasBorder
import com.example.ui.theme.AtlasCyan
import com.example.ui.theme.AtlasSurface
import com.example.ui.theme.AtlasSurfaceVariant
import com.example.ui.theme.AtlasTextMuted
import com.example.ui.theme.AtlasTextPrimary
import com.example.ui.theme.AtlasTextSecondary
import com.example.ui.theme.AtlasViolet
import com.example.ui.viewmodel.CodeAtlasViewModel

import androidx.compose.material.icons.filled.AutoAwesome

@Composable
fun SearchResultsScreen(
    viewModel: CodeAtlasViewModel,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToAi: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val query by viewModel.searchQuery.collectAsState()
    val suggestions by viewModel.autocompleteSuggestions.collectAsState()
    val filters by viewModel.filters.collectAsState()
    val results by viewModel.searchResults.collectAsState()
    val languages by viewModel.languages.collectAsState()

    val categories = listOf(
        "Keywords",
        "Concepts",
        "Functions and methods",
        "Syntax and operators",
        "Coding questions",
        "Errors and debugging",
        "Algorithms and data structures"
    )

    val difficulties = listOf("Beginner", "Intermediate", "Advanced")

    val hasActiveFilters = filters.languageSlug != null || filters.category != null || filters.difficulty != null

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("search_results_screen")
    ) {
        // Sticky Search Bar with Autocomplete Dropdown
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AtlasSurface)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            SearchBarWithAutocomplete(
                query = query,
                onQueryChange = { viewModel.onQueryChange(it) },
                suggestions = suggestions,
                onSearch = { viewModel.performSearch(it) },
                onSuggestionClick = { suggestion ->
                    if (suggestion.entryId != null) {
                        viewModel.selectEntry(suggestion.entryId)
                        onNavigateToDetail(suggestion.entryId)
                    } else {
                        viewModel.performSearch(suggestion.targetQuery)
                    }
                },
                placeholderText = "Filter by keywords, functions, syntax, errors..."
            )
        }

        // Horizontal Category Filters
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(AtlasSurface)
                .padding(bottom = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChipPill(
                    label = "All Categories",
                    isSelected = filters.category == null,
                    onClick = { viewModel.setCategoryFilter(null) }
                )
            }
            items(categories) { category ->
                FilterChipPill(
                    label = category,
                    isSelected = filters.category == category,
                    onClick = { viewModel.setCategoryFilter(category) }
                )
            }
        }

        // Horizontal Language Filters
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(AtlasSurface)
                .padding(bottom = 10.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChipPill(
                    label = "All Languages",
                    isSelected = filters.languageSlug == null,
                    onClick = { viewModel.setLanguageFilter(null) },
                    isLanguage = true
                )
            }
            items(languages) { lang ->
                FilterChipPill(
                    label = lang.name,
                    isSelected = filters.languageSlug == lang.slug,
                    onClick = { viewModel.setLanguageFilter(lang.slug) },
                    dotColor = try {
                        Color(android.graphics.Color.parseColor(lang.colorHex))
                    } catch (_: Exception) {
                        null
                    },
                    isLanguage = true
                )
            }
        }

        // Result Count & Reset Filters Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${results.size} ${if (results.size == 1) "concept found" else "concepts found"}",
                style = MaterialTheme.typography.labelMedium,
                color = AtlasTextSecondary,
                fontWeight = FontWeight.Medium
            )

            AnimatedVisibility(visible = hasActiveFilters || query.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(AtlasSurfaceVariant)
                        .clickable { viewModel.clearAllSearchAndFilters() }
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = AtlasCyan,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Reset All",
                        style = MaterialTheme.typography.labelSmall,
                        color = AtlasCyan
                    )
                }
            }
        }

        // Results List or Empty State
        if (results.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(AtlasSurfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = null,
                            tint = AtlasTextMuted,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "No matching concepts found",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AtlasTextPrimary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Try searching for keywords like 'async', 'recursion', 'join', or clear active filters.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AtlasTextSecondary,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { viewModel.clearAllSearchAndFilters() },
                            colors = ButtonDefaults.buttonColors(containerColor = AtlasSurfaceVariant),
                            border = androidx.compose.foundation.BorderStroke(1.dp, AtlasBorder),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Clear Filters",
                                color = AtlasTextPrimary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Button(
                            onClick = { onNavigateToAi(query) },
                            colors = ButtonDefaults.buttonColors(containerColor = AtlasViolet),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Solve with AI",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(results, key = { it.entry.id }) { item ->
                    KnowledgeCard(
                        entryWithBookmark = item,
                        onClick = {
                            viewModel.selectEntry(item.entry.id)
                            onNavigateToDetail(item.entry.id)
                        },
                        onToggleBookmark = {
                            viewModel.toggleBookmark(item.entry.id)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterChipPill(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    dotColor: Color? = null,
    isLanguage: Boolean = false
) {
    val activeBg = if (isLanguage) AtlasViolet.copy(alpha = 0.2f) else AtlasCyan.copy(alpha = 0.2f)
    val activeBorder = if (isLanguage) AtlasViolet else AtlasCyan
    val activeText = if (isLanguage) AtlasViolet else AtlasCyan

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (isSelected) activeBg else AtlasSurfaceVariant)
            .border(
                1.dp,
                if (isSelected) activeBorder else AtlasBorder,
                RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (dotColor != null) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(dotColor)
                )
                Spacer(modifier = Modifier.width(6.dp))
            }
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) activeText else AtlasTextSecondary
            )
        }
    }
}

package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.data.model.AutocompleteSuggestion
import com.example.data.model.SuggestionType
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

@Composable
fun SearchBarWithAutocomplete(
    query: String,
    onQueryChange: (String) -> Unit,
    suggestions: List<AutocompleteSuggestion>,
    onSearch: (String) -> Unit,
    onSuggestionClick: (AutocompleteSuggestion) -> Unit,
    modifier: Modifier = Modifier,
    placeholderText: String = "Search keywords, syntax, errors, questions...",
    focusRequester: FocusRequester? = null
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    val showDropdown = isFocused && query.isNotBlank()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(10f)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Main Search Input
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (focusRequester != null) Modifier.focusRequester(focusRequester)
                        else Modifier
                    )
                    .onFocusChanged { isFocused = it.isFocused }
                    .testTag("search_bar_input"),
                placeholder = {
                    Text(
                        text = placeholderText,
                        color = AtlasTextMuted,
                        fontSize = 14.5.sp,
                        maxLines = 1
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = if (isFocused) AtlasCyan else AtlasTextSecondary,
                        modifier = Modifier.size(22.dp)
                    )
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                onQueryChange("")
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .testTag("clear_search_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear search",
                                tint = AtlasTextSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = AtlasSurfaceVariant,
                    unfocusedContainerColor = AtlasSurface,
                    focusedBorderColor = AtlasCyan,
                    unfocusedBorderColor = AtlasBorder,
                    focusedTextColor = AtlasTextPrimary,
                    unfocusedTextColor = AtlasTextPrimary,
                    cursorColor = AtlasCyan
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        onSearch(query)
                    }
                )
            )

            // Autocomplete Dropdown List
            AnimatedVisibility(
                visible = showDropdown,
                enter = fadeIn(tween(150)) + expandVertically(tween(200)),
                exit = fadeOut(tween(150)) + shrinkVertically(tween(200))
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp)
                        .shadow(12.dp, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(AtlasSurface)
                        .border(1.dp, AtlasBorder, RoundedCornerShape(16.dp))
                        .testTag("autocomplete_dropdown"),
                    color = AtlasSurface
                ) {
                    if (suggestions.isEmpty()) {
                        // Empty Suggestions State
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    focusManager.clearFocus()
                                    onSearch(query)
                                }
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = AtlasTextMuted,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "No direct suggestions for \"$query\"",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = AtlasTextSecondary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Tap here or press Enter to search full database",
                                style = MaterialTheme.typography.labelSmall,
                                color = AtlasCyan,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    } else {
                        // List of dynamic suggestions
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 280.dp)
                        ) {
                            items(suggestions, key = { it.id }) { suggestion ->
                                SuggestionRowItem(
                                    suggestion = suggestion,
                                    query = query,
                                    onClick = {
                                        focusManager.clearFocus()
                                        onSuggestionClick(suggestion)
                                    }
                                )
                                HorizontalDivider(
                                    color = AtlasBorder.copy(alpha = 0.5f),
                                    thickness = 0.5.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SuggestionRowItem(
    suggestion: AutocompleteSuggestion,
    query: String,
    onClick: () -> Unit
) {
    val (typeLabel, badgeColor, icon) = when (suggestion.type) {
        SuggestionType.KEYWORD -> Triple("KEYWORD", AtlasOrange, Icons.Default.Code)
        SuggestionType.FUNCTION -> Triple("FUNCTION", AtlasViolet, Icons.Default.Functions)
        SuggestionType.CONCEPT -> Triple("CONCEPT", AtlasCyan, Icons.Default.Lightbulb)
        SuggestionType.ERROR -> Triple("ERROR", AtlasRed, Icons.Default.Warning)
        SuggestionType.QUESTION -> Triple("Q&A", AtlasYellow, Icons.Default.HelpOutline)
        SuggestionType.LANGUAGE -> Triple("LANGUAGE", AtlasGreen, Icons.Default.Language)
        SuggestionType.TRENDING -> Triple("TRENDING", AtlasCyan, Icons.Default.TrendingUp)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .testTag("suggestion_item_${suggestion.id}"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Type Badge Icon
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(badgeColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = badgeColor,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Title with highlighted query and subtitle
        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = highlightQuery(suggestion.title, query),
                    style = MaterialTheme.typography.bodyMedium,
                    color = AtlasTextPrimary,
                    maxLines = 1,
                    modifier = Modifier.weight(1f, fill = false)
                )

                // Optional Language tag if present
                if (suggestion.language != null) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(AtlasSurfaceVariant)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = suggestion.language,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            color = AtlasTextSecondary
                        )
                    }
                }
            }

            if (suggestion.subtitle.isNotBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = suggestion.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.5.sp,
                    color = AtlasTextMuted,
                    maxLines = 1
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Tag label (e.g. KEYWORD, ERROR)
        Text(
            text = typeLabel,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 9.5.sp,
            fontWeight = FontWeight.Bold,
            color = badgeColor
        )

        Spacer(modifier = Modifier.width(6.dp))

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Select suggestion",
            tint = AtlasTextMuted,
            modifier = Modifier.size(14.dp)
        )
    }
}

private fun highlightQuery(text: String, query: String): androidx.compose.ui.text.AnnotatedString {
    if (query.isBlank()) return androidx.compose.ui.text.AnnotatedString(text)

    val lowerText = text.lowercase()
    val lowerQuery = query.lowercase().trim()
    val startIndex = lowerText.indexOf(lowerQuery)

    if (startIndex < 0) return androidx.compose.ui.text.AnnotatedString(text)

    val endIndex = startIndex + lowerQuery.length

    return buildAnnotatedString {
        append(text.substring(0, startIndex))
        withStyle(
            SpanStyle(
                color = AtlasCyan,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(text.substring(startIndex, endIndex))
        }
        append(text.substring(endIndex))
    }
}

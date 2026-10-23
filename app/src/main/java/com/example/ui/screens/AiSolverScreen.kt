package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.SyntaxCodeBlock
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

@Composable
fun AiSolverScreen(
    viewModel: CodeAtlasViewModel,
    modifier: Modifier = Modifier
) {
    val aiQuery by viewModel.aiQuery.collectAsState()
    val aiSolution by viewModel.aiSolution.collectAsState()
    val isAiLoading by viewModel.isAiLoading.collectAsState()
    val focusManager = LocalFocusManager.current

    val samplePrompts = listOf(
        "Loop in Python with enumerate and while",
        "Fix TypeError: Cannot read properties of undefined in JavaScript",
        "How to reverse a string in Rust",
        "SQL query for total orders per customer",
        "Coroutine cancellation in Kotlin"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("ai_solver_screen"),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(AtlasViolet.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = AtlasViolet,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "CodeAtlas AI Assistant",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = AtlasTextPrimary
                        )
                        Text(
                            text = "Ask any programming question, debugging error, or code generation request.",
                            style = MaterialTheme.typography.bodySmall,
                            color = AtlasTextSecondary
                        )
                    }
                }
            }
        }

        // Search / Question Input
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = aiQuery,
                    onValueChange = { viewModel.updateAiQuery(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("ai_prompt_input"),
                    placeholder = {
                        Text(
                            text = "e.g. 'How to loop a dictionary in Python', 'Debug NullPointerException'...",
                            color = AtlasTextMuted,
                            fontSize = 13.5.sp
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = AtlasViolet,
                            modifier = Modifier.size(22.dp)
                        )
                    },
                    trailingIcon = {
                        if (aiQuery.isNotBlank()) {
                            IconButton(onClick = { viewModel.clearAi() }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = AtlasTextMuted,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = AtlasSurfaceVariant,
                        unfocusedContainerColor = AtlasSurface,
                        focusedBorderColor = AtlasViolet,
                        unfocusedBorderColor = AtlasBorder,
                        focusedTextColor = AtlasTextPrimary,
                        unfocusedTextColor = AtlasTextPrimary,
                        cursorColor = AtlasViolet
                    ),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            focusManager.clearFocus()
                            viewModel.askAi(aiQuery)
                        }
                    ),
                    maxLines = 4
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.askAi(aiQuery)
                        },
                        enabled = aiQuery.isNotBlank() && !isAiLoading,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AtlasViolet,
                            disabledContainerColor = AtlasSurfaceVariant
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.testTag("ai_solve_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isAiLoading) "Generating..." else "Solve Problem",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Quick Suggestion Chips
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "TRY ASKING:",
                    style = MaterialTheme.typography.labelSmall,
                    color = AtlasTextMuted,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(samplePrompts) { prompt ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(AtlasSurfaceVariant)
                                .border(1.dp, AtlasBorder, RoundedCornerShape(16.dp))
                                .clickable {
                                    viewModel.updateAiQuery(prompt)
                                    viewModel.askAi(prompt)
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = prompt,
                                style = MaterialTheme.typography.bodySmall,
                                fontSize = 12.sp,
                                color = AtlasTextPrimary
                            )
                        }
                    }
                }
            }
        }

        // Loading State
        if (isAiLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(
                            color = AtlasViolet,
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "CodeAtlas AI is compiling solution...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = AtlasTextSecondary
                        )
                    }
                }
            }
        }

        // AI Solution Result Card
        if (aiSolution != null && !isAiLoading) {
            item {
                val solution = aiSolution!!
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, AtlasViolet.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                        .testTag("ai_solution_card"),
                    colors = CardDefaults.cardColors(containerColor = AtlasSurface)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        // Badge header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(AtlasGreen)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "AI SOLUTION GENERATED",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AtlasGreen,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            }

                            if (solution.language != null) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(AtlasSurfaceVariant)
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = solution.language,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AtlasCyan,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Query Title
                        Text(
                            text = solution.query,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = AtlasTextPrimary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Explanation
                        Text(
                            text = solution.explanation,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AtlasTextSecondary,
                            lineHeight = 22.sp
                        )

                        // Generated Code Snippet
                        if (!solution.codeSnippet.isNullOrBlank()) {
                            Spacer(modifier = Modifier.height(14.dp))
                            SyntaxCodeBlock(
                                code = solution.codeSnippet,
                                language = solution.language ?: "Code"
                            )
                        }

                        // Debugging Tips
                        if (solution.tips.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "KEY BEST PRACTICES & TIPS",
                                style = MaterialTheme.typography.labelSmall,
                                color = AtlasTextMuted,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            solution.tips.forEach { tip ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 3.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = AtlasCyan,
                                        modifier = Modifier
                                            .size(16.dp)
                                            .padding(top = 2.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = tip,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AtlasTextPrimary,
                                        lineHeight = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AtlasBorder
import com.example.ui.theme.AtlasTextMuted
import com.example.ui.theme.AtlasTextSecondary
import com.example.ui.theme.CodeBg
import com.example.ui.theme.CodeComment
import com.example.ui.theme.CodeFunction
import com.example.ui.theme.CodeKeyword
import com.example.ui.theme.CodeNumber
import com.example.ui.theme.CodeOperator
import com.example.ui.theme.CodeString
import com.example.ui.theme.CodeType
import kotlinx.coroutines.delay

@Composable
fun SyntaxCodeBlock(
    code: String,
    language: String? = "Code",
    modifier: Modifier = Modifier,
    showLineNumbers: Boolean = true
) {
    val context = LocalContext.current
    var copied by remember { mutableStateOf(false) }

    LaunchedEffect(copied) {
        if (copied) {
            delay(2000)
            copied = false
        }
    }

    val lines = remember(code) { code.lines() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CodeBg)
            .border(1.dp, AtlasBorder, RoundedCornerShape(12.dp))
            .testTag("syntax_code_block")
    ) {
        // Code Block Header with terminal dots and copy button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF161B22))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Window dots
                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFFF5F56)))
                Spacer(modifier = Modifier.width(6.dp))
                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFFFFBD2E)))
                Spacer(modifier = Modifier.width(6.dp))
                Box(modifier = Modifier.size(10.dp).clip(CircleShape).background(Color(0xFF27C93F)))
                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = language?.uppercase() ?: "CODE",
                    style = MaterialTheme.typography.labelSmall,
                    color = AtlasTextSecondary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            IconButton(
                onClick = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboard.setPrimaryClip(ClipData.newPlainText("Code", code))
                    copied = true
                    Toast.makeText(context, "Code copied to clipboard", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .size(32.dp)
                    .testTag("copy_code_button")
            ) {
                if (copied) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Copied",
                        tint = Color(0xFF39D353),
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = "Copy code",
                        tint = AtlasTextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Code Content with syntax highlighting & optional line numbers
        val horizontalScrollState = rememberScrollState()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            if (showLineNumbers) {
                Column(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    lines.forEachIndexed { index, _ ->
                        Text(
                            text = "${index + 1}",
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.sp,
                            color = AtlasTextMuted,
                            lineHeight = 20.sp
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height((lines.size * 20).dp)
                        .background(AtlasBorder)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(horizontalScrollState)
                    .padding(horizontal = 12.dp)
            ) {
                Column {
                    lines.forEach { line ->
                        Text(
                            text = highlightSyntax(line),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 12.5.sp,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}

private fun highlightSyntax(line: String): AnnotatedString {
    return buildAnnotatedString {
        val trimmed = line.trimStart()

        // 1. Comments
        if (trimmed.startsWith("//") || trimmed.startsWith("#") || trimmed.startsWith("--") || trimmed.startsWith("/*")) {
            append(line)
            addStyle(SpanStyle(color = CodeComment), 0, line.length)
            return@buildAnnotatedString
        }

        append(line)

        val keywords = listOf(
            "const", "let", "var", "function", "def", "async", "await", "return",
            "if", "else", "for", "while", "class", "import", "from", "export",
            "val", "var", "suspend", "fun", "public", "private", "struct", "fn",
            "match", "impl", "type", "interface", "package", "select", "from",
            "where", "join", "inner", "left", "group", "by", "order", "case",
            "then", "end", "new", "throw", "try", "catch", "finally", "yield",
            "guard", "switch", "default", "defer", "go", "make", "chan"
        )

        val types = listOf(
            "String", "Int", "Boolean", "Float", "Double", "List", "Map", "Set",
            "Promise", "Response", "Array", "T", "TData", "Result", "Option", "usize"
        )

        // Highlight strings
        val stringRegex = Regex("(\"[^\"]*\"|'[^']*'|`[^`]*`)")
        stringRegex.findAll(line).forEach { match ->
            addStyle(SpanStyle(color = CodeString), match.range.first, match.range.last + 1)
        }

        // Highlight keywords
        val wordRegex = Regex("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b")
        wordRegex.findAll(line).forEach { match ->
            val word = match.value
            val start = match.range.first
            val end = match.range.last + 1

            if (keywords.contains(word.lowercase())) {
                addStyle(SpanStyle(color = CodeKeyword, fontWeight = FontWeight.Bold), start, end)
            } else if (types.contains(word)) {
                addStyle(SpanStyle(color = CodeType), start, end)
            } else if (match.range.last + 1 < line.length && line[match.range.last + 1] == '(') {
                addStyle(SpanStyle(color = CodeFunction), start, end)
            }
        }

        // Highlight numbers
        val numberRegex = Regex("\\b\\d+(\\.\\d+)?\\b")
        numberRegex.findAll(line).forEach { match ->
            addStyle(SpanStyle(color = CodeNumber), match.range.first, match.range.last + 1)
        }

        // Highlight operators
        val operatorRegex = Regex("(=>|==|!=|<=|>=|->|::|&&|\\|\\||\\+=|-=|\\*=|/=|\\+|-|\\*|/|%|<|>|!)")
        operatorRegex.findAll(line).forEach { match ->
            addStyle(SpanStyle(color = CodeOperator), match.range.first, match.range.last + 1)
        }
    }
}

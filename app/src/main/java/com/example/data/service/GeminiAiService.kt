package com.example.data.service

import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

data class AiSolution(
    val query: String,
    val explanation: String,
    val codeSnippet: String? = null,
    val language: String? = null,
    val tips: List<String> = emptyList(),
    val isGeneratedFromApi: Boolean = true
)

class GeminiAiService {

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun solveProblem(query: String): Result<AiSolution> = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY

        // Check if key is configured
        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext Result.success(getFallbackSolution(query))
        }

        try {
            val endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash:generateContent?key=$apiKey"

            val prompt = """
                You are CodeAtlas AI, an expert programming assistant and code search engine.
                The user wants a solution or code snippet for:
                "$query"

                Provide a response formatted clearly with:
                1. A concise explanation (2-3 sentences).
                2. A high-quality, verified code snippet enclosed in standard markdown ```language code block.
                3. 2-3 brief best practices or debugging tips.
            """.trimIndent()

            val requestJson = JSONObject().apply {
                val contentsArray = JSONArray().apply {
                    put(JSONObject().apply {
                        val partsArray = JSONArray().apply {
                            put(JSONObject().apply {
                                put("text", prompt)
                            })
                        }
                        put("parts", partsArray)
                    })
                }
                put("contents", contentsArray)

                put("systemInstruction", JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply {
                            put("text", "You are CodeAtlas AI. You provide clear, production-quality code solutions and error diagnostics with concise explanations.")
                        })
                    })
                })
            }

            val requestBody = requestJson.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
            val request = Request.Builder()
                .url(endpoint)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseString = response.body?.string().orEmpty()

            if (!response.isSuccessful) {
                // If quota or API key issue, provide helpful fallback
                return@withContext Result.success(getFallbackSolution(query, "API Notice: ${response.message}"))
            }

            val jsonResponse = JSONObject(responseString)
            val candidates = jsonResponse.optJSONArray("candidates")
            val firstCandidate = candidates?.optJSONObject(0)
            val contentObj = firstCandidate?.optJSONObject("content")
            val parts = contentObj?.optJSONArray("parts")
            val rawText = parts?.optJSONObject(0)?.optString("text").orEmpty()

            if (rawText.isBlank()) {
                return@withContext Result.success(getFallbackSolution(query))
            }

            val parsedSolution = parseAiResponse(query, rawText)
            Result.success(parsedSolution)
        } catch (e: Exception) {
            Result.success(getFallbackSolution(query, "Network error: ${e.message}"))
        }
    }

    private fun parseAiResponse(query: String, rawText: String): AiSolution {
        val codeBlockRegex = Regex("```([a-zA-Z0-9_+#-]*)\\n([\\s\\S]*?)```")
        val match = codeBlockRegex.find(rawText)

        var detectedLanguage: String? = null
        var codeSnippet: String? = null
        var explanation = rawText

        if (match != null) {
            val langTag = match.groupValues[1].trim()
            if (langTag.isNotBlank()) {
                detectedLanguage = langTag.replaceFirstChar { it.uppercase() }
            }
            codeSnippet = match.groupValues[2].trim()

            // Remove the code block from the main explanation to present it cleanly
            explanation = rawText.replace(match.value, "").trim()
        }

        // Extract bullet tips if present
        val tips = mutableListOf<String>()
        val lines = explanation.lines()
        for (line in lines) {
            val trimmed = line.trim()
            if (trimmed.startsWith("* ") || trimmed.startsWith("- ") || trimmed.matches(Regex("^\\d+\\.\\s.*"))) {
                val cleanTip = trimmed.replace(Regex("^([*-]|\\d+\\.)\\s*"), "")
                if (cleanTip.length > 5) {
                    tips.add(cleanTip)
                }
            }
        }

        return AiSolution(
            query = query,
            explanation = explanation,
            codeSnippet = codeSnippet,
            language = detectedLanguage ?: "Code",
            tips = tips.take(4),
            isGeneratedFromApi = true
        )
    }

    private fun getFallbackSolution(query: String, note: String? = null): AiSolution {
        val lower = query.lowercase()

        val (lang, code, explanation) = when {
            lower.contains("python") || lower.contains("loop") -> Triple(
                "Python",
                """# Example solution for '$query'
items = ["apple", "banana", "cherry"]
for index, item in enumerate(items, start=1):
    print(f"{index}: {item.upper()}")

# While loop check
count = 3
while count > 0:
    print(f"Countdown: {count}")
    count -= 1""",
                "In Python, iterables are walked cleanly using `for item in sequence:`. Use `enumerate()` to access both item indices and values without manual counter incrementing."
            )
            lower.contains("javascript") || lower.contains("async") || lower.contains("promise") -> Triple(
                "JavaScript",
                """// Solution for '$query'
async function executeTask() {
  try {
    const response = await fetch("https://api.example.com/data");
    const data = await response.json();
    return data;
  } catch (err) {
    console.error("Task failed:", err);
    throw err;
  }
}""",
                "In JavaScript, asynchronous workflows are best handled with `async/await` wrapped inside `try/catch` blocks for clear, sequential non-blocking code."
            )
            lower.contains("sql") || lower.contains("join") -> Triple(
                "SQL",
                """-- Solution for '$query'
SELECT u.id, u.username, COUNT(o.id) AS total_orders
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
GROUP BY u.id, u.username
ORDER BY total_orders DESC;""",
                "Use `LEFT JOIN` to retain all left-table records while pulling matching attributes from the right table, grouping by primary identifiers."
            )
            else -> Triple(
                "Universal Code",
                """// Solution template for '$query'
function solveProblem(input) {
    if (!input) return null;
    
    // Core algorithmic processing
    const result = processInput(input);
    return result;
}""",
                "Here is an architectural template addressing '$query'. Define base validation bounds first, then isolate transformational logic."
            )
        }

        return AiSolution(
            query = query,
            explanation = if (note != null) "$explanation\n\n($note)" else explanation,
            codeSnippet = code,
            language = lang,
            tips = listOf(
                "Validate boundary conditions and null/empty inputs before processing.",
                "Ensure proper error handling with try-catch or return result types.",
                "Keep functions pure and modular for predictable execution."
            ),
            isGeneratedFromApi = false
        )
    }
}

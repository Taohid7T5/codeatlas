package com.example

import com.example.data.local.PrepopulatedData
import com.example.data.model.AutocompleteSuggestion
import com.example.data.model.SuggestionType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun autocompleteSuggestion_initialization() {
        val suggestion = AutocompleteSuggestion(
            id = "test_1",
            title = "async/await",
            subtitle = "Asynchronous javascript syntax",
            language = "JavaScript",
            type = SuggestionType.KEYWORD,
            targetQuery = "async/await"
        )

        assertEquals("async/await", suggestion.title)
        assertEquals(SuggestionType.KEYWORD, suggestion.type)
        assertEquals("JavaScript", suggestion.language)
        assertNotNull(suggestion.targetQuery)
    }

    @Test
    fun queryHighlight_calculation() {
        val title = "Recursion in Python"
        val query = "recur"
        assertTrue(title.lowercase().contains(query.lowercase()))
    }

    @Test
    fun combinationSearch_pythonLoop_isFoundInPrepopulatedData() {
        val loopEntries = PrepopulatedData.entries.filter {
            it.languageSlug == "python" && (it.title.contains("Loop", ignoreCase = true) || it.tags.contains("loop in python"))
        }

        assertTrue("Expected Python loop entry in prepopulated entries", loopEntries.isNotEmpty())
        val pythonLoop = loopEntries.first()
        assertTrue(pythonLoop.exampleCode.contains("for lang in languages:"))
        assertTrue(pythonLoop.exampleCode.contains("while countdown > 0:"))
        assertTrue(pythonLoop.exampleCode.contains("enumerate("))
    }

    @Test
    fun aiSolution_creation() {
        val solution = com.example.data.service.AiSolution(
            query = "Reverse a list in Python",
            explanation = "Use extended slice [::-1]",
            codeSnippet = "rev = lst[::-1]",
            language = "Python",
            tips = listOf("Constant time view"),
            isGeneratedFromApi = false
        )
        assertEquals("Python", solution.language)
        assertNotNull(solution.codeSnippet)
    }
}

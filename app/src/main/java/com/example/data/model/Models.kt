package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
data class LanguageEntity(
    @PrimaryKey val id: String, // e.g. "python", "javascript"
    val name: String,
    val slug: String,
    val description: String,
    val officialDocUrl: String,
    val category: String, // "General", "Systems", "Web", "Data", "Scripting"
    val colorHex: String,
    val badgeIconName: String = ""
)

@Entity(tableName = "knowledge_entries")
data class KnowledgeEntryEntity(
    @PrimaryKey val id: String,
    val title: String,
    val slug: String,
    val definition: String,
    val contentType: String, // "Keyword", "Concept", "Function", "Syntax", "Question", "Error", "Algorithm", "Framework"
    val languageSlug: String?, // null if multi-language / general concept
    val languageName: String?,
    val syntax: String,
    val exampleCode: String,
    val exampleExplanation: String,
    val commonMistakes: String = "",
    val difficulty: String = "Beginner", // "Beginner", "Intermediate", "Advanced"
    val tags: String, // comma-separated
    val sourceUrl: String,
    val sourceName: String,
    val relatedConcepts: String = "", // comma-separated slugs or titles
    val isTrending: Boolean = false,
    val popularityScore: Int = 0
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val id: String,
    val entryId: String,
    val savedAt: Long = System.currentTimeMillis(),
    val privateNote: String = ""
)

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val query: String,
    val timestamp: Long = System.currentTimeMillis()
)

data class KnowledgeEntryWithBookmark(
    val entry: KnowledgeEntryEntity,
    val isBookmarked: Boolean,
    val privateNote: String = ""
)

enum class SuggestionType {
    KEYWORD,
    FUNCTION,
    CONCEPT,
    ERROR,
    QUESTION,
    LANGUAGE,
    TRENDING
}

data class AutocompleteSuggestion(
    val id: String,
    val title: String,
    val subtitle: String,
    val language: String? = null,
    val type: SuggestionType,
    val entryId: String? = null,
    val targetQuery: String
)

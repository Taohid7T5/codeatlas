package com.example.data.repository

import com.example.data.local.BookmarkDao
import com.example.data.local.KnowledgeEntryDao
import com.example.data.local.LanguageDao
import com.example.data.local.PrepopulatedData
import com.example.data.local.SearchHistoryDao
import com.example.data.model.AutocompleteSuggestion
import com.example.data.model.BookmarkEntity
import com.example.data.model.KnowledgeEntryEntity
import com.example.data.model.KnowledgeEntryWithBookmark
import com.example.data.model.LanguageEntity
import com.example.data.model.SearchHistoryEntity
import com.example.data.model.SuggestionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class CodeAtlasRepository(
    private val languageDao: LanguageDao,
    private val entryDao: KnowledgeEntryDao,
    private val bookmarkDao: BookmarkDao,
    private val searchHistoryDao: SearchHistoryDao
) {
    fun getAllLanguages(): Flow<List<LanguageEntity>> = languageDao.getAllLanguages()

    fun getTrendingEntries(): Flow<List<KnowledgeEntryEntity>> = entryDao.getTrendingEntries()

    fun getRecentSearches(): Flow<List<SearchHistoryEntity>> = searchHistoryDao.getRecentSearches()

    fun searchEntries(
        query: String,
        languageSlug: String?,
        contentType: String?,
        difficulty: String?
    ): Flow<List<KnowledgeEntryWithBookmark>> {
        val cleanQuery = query.trim()
        val entriesFlow = entryDao.getAllEntries()
        val bookmarksFlow = bookmarkDao.getAllBookmarks()

        return combine(entriesFlow, bookmarksFlow) { entries, bookmarks ->
            val bookmarkMap = bookmarks.associateBy { it.entryId }
            entries.mapNotNull { entry ->
                val score = scoreEntry(entry, cleanQuery, languageSlug, contentType, difficulty)
                if (score > 0) Pair(entry, score) else null
            }
            .sortedByDescending { it.second }
            .map { (entry, _) ->
                val bm = bookmarkMap[entry.id]
                KnowledgeEntryWithBookmark(
                    entry = entry,
                    isBookmarked = bm != null,
                    privateNote = bm?.privateNote.orEmpty()
                )
            }
        }
    }

    private fun scoreEntry(
        entry: KnowledgeEntryEntity,
        cleanQuery: String,
        languageSlug: String?,
        contentType: String?,
        difficulty: String?
    ): Int {
        if (languageSlug != null && entry.languageSlug != languageSlug) return 0
        if (contentType != null && entry.contentType != contentType) return 0
        if (difficulty != null && entry.difficulty != difficulty) return 0

        if (cleanQuery.isEmpty()) return 1

        val queryLower = cleanQuery.lowercase()
        val stopWords = setOf(
            "in", "the", "a", "an", "user", "search", "code", "how", "to", "show",
            "me", "what", "is", "of", "and", "or", "for", "with"
        )
        val rawTokens = queryLower.split(Regex("[\\s,]+")).filter { it.isNotBlank() }
        val tokens = rawTokens.filter { it !in stopWords }.ifEmpty { rawTokens }

        val titleLower = entry.title.lowercase()
        val tagsLower = entry.tags.lowercase()
        val syntaxLower = entry.syntax.lowercase()
        val defLower = entry.definition.lowercase()
        val langNameLower = entry.languageName?.lowercase().orEmpty()
        val langSlugLower = entry.languageSlug?.lowercase().orEmpty()

        var score = 0

        // Exact title match
        if (titleLower == queryLower) {
            score += 600
        } else if (titleLower.contains(queryLower)) {
            score += 350
        }

        // Full phrase match in tags or syntax
        if (tagsLower.contains(queryLower)) score += 300
        if (syntaxLower.contains(queryLower)) score += 200

        // Check if query explicitly mentions the entry's programming language
        val mentionsLang = tokens.any { token ->
            token == langNameLower || token == langSlugLower ||
            (token == "js" && langSlugLower == "javascript") ||
            (token == "ts" && langSlugLower == "typescript") ||
            (token == "py" && langSlugLower == "python") ||
            (token == "kt" && langSlugLower == "kotlin") ||
            (token == "golang" && langSlugLower == "go") ||
            (token == "cpp" && langSlugLower == "cplusplus") ||
            (token == "c++" && langSlugLower == "cplusplus") ||
            (token == "c#" && langSlugLower == "csharp")
        }

        if (mentionsLang) {
            score += 100
        }

        // Token-based matching
        var matchedTokensCount = 0
        for (t in tokens) {
            var tokenHit = false
            if (titleLower.contains(t)) {
                score += 80
                tokenHit = true
            }
            if (tagsLower.contains(t)) {
                score += 60
                tokenHit = true
            }
            if (syntaxLower.contains(t)) {
                score += 40
                tokenHit = true
            }
            if (defLower.contains(t)) {
                score += 20
                tokenHit = true
            }
            if (langNameLower == t || langSlugLower == t) {
                score += 40
                tokenHit = true
            }
            if (tokenHit) matchedTokensCount++
        }

        // High bonus when all non-stopword query tokens match (e.g. "loop" AND "python")
        if (tokens.size > 1 && matchedTokensCount >= tokens.size) {
            score += 250
        }

        if (score > 0) {
            score += entry.popularityScore
        }

        return score
    }

    suspend fun getEntryWithBookmark(id: String): KnowledgeEntryWithBookmark? {
        val entry = entryDao.getEntryById(id) ?: return null
        val bm = bookmarkDao.getBookmarkByEntryId(id)
        return KnowledgeEntryWithBookmark(
            entry = entry,
            isBookmarked = bm != null,
            privateNote = bm?.privateNote.orEmpty()
        )
    }

    fun observeEntryWithBookmark(id: String): Flow<KnowledgeEntryWithBookmark?> {
        val entryFlow = entryDao.getAllEntries()
        val bookmarkFlow = bookmarkDao.observeBookmarkByEntryId(id)
        return combine(entryFlow, bookmarkFlow) { allEntries, bm ->
            val entry = allEntries.firstOrNull { it.id == id } ?: return@combine null
            KnowledgeEntryWithBookmark(
                entry = entry,
                isBookmarked = bm != null,
                privateNote = bm?.privateNote.orEmpty()
            )
        }
    }

    suspend fun getAutocompleteSuggestions(query: String): List<AutocompleteSuggestion> {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return emptyList()

        val results = mutableListOf<AutocompleteSuggestion>()
        val lower = trimmed.lowercase()

        // 1. Language matches
        val allLangs = languageDao.getAllLanguages().first()
        val matchingLangs = allLangs.filter {
            it.name.lowercase().contains(lower) || it.slug.lowercase().contains(lower)
        }.take(2)

        for (lang in matchingLangs) {
            results.add(
                AutocompleteSuggestion(
                    id = "lang_${lang.slug}",
                    title = "${lang.name} Language",
                    subtitle = "Browse all ${lang.name} keywords & concepts",
                    language = lang.name,
                    type = SuggestionType.LANGUAGE,
                    targetQuery = lang.name
                )
            )
        }

        // 2. Intelligent scored entry matches (supports combination queries like "loop in python")
        val allEntries = entryDao.getAllEntries().first()
        val scoredEntries = allEntries.mapNotNull { entry ->
            val score = scoreEntry(entry, trimmed, null, null, null)
            if (score > 0) Pair(entry, score) else null
        }
        .sortedByDescending { it.second }
        .take(5)

        for ((item, _) in scoredEntries) {
            val suggestionType = when (item.contentType) {
                "Keywords" -> SuggestionType.KEYWORD
                "Functions and methods" -> SuggestionType.FUNCTION
                "Errors and debugging" -> SuggestionType.ERROR
                "Coding questions" -> SuggestionType.QUESTION
                "Syntax and operators" -> SuggestionType.KEYWORD
                else -> SuggestionType.CONCEPT
            }

            // Preview syntax snippet if available for immediate code glance
            val previewSnippet = if (item.syntax.isNotBlank()) {
                item.syntax.lines().firstOrNull() ?: item.definition
            } else {
                item.definition
            }

            results.add(
                AutocompleteSuggestion(
                    id = "entry_${item.id}",
                    title = item.title,
                    subtitle = previewSnippet.take(80) + if (previewSnippet.length > 80) "..." else "",
                    language = item.languageName,
                    type = suggestionType,
                    entryId = item.id,
                    targetQuery = item.title
                )
            )
        }

        // 3. Combination phrases & trending query suggestions
        val popularPhrases = listOf(
            "loop in python",
            "for loop in python",
            "loop in javascript",
            "functions in python",
            "error handling in python",
            "recursion in python",
            "reverse string in python",
            "sql joins inner left",
            "async/await javascript",
            "typeerror nonetype python",
            "rust ownership & borrowing",
            "binary search algorithm",
            "difference between let and const",
            "kotlin coroutines suspend",
            "nullpointerexception java"
        )
        val matchingPhrases = popularPhrases.filter { phrase ->
            phrase.contains(lower) && results.none { res -> res.title.equals(phrase, ignoreCase = true) }
        }.take(2)

        for (phrase in matchingPhrases) {
            results.add(
                AutocompleteSuggestion(
                    id = "phrase_$phrase",
                    title = phrase,
                    subtitle = "Popular combination search query",
                    type = SuggestionType.TRENDING,
                    targetQuery = phrase
                )
            )
        }

        return results.take(7)
    }

    suspend fun recordSearch(query: String) {
        val trimmed = query.trim()
        if (trimmed.isNotBlank()) {
            searchHistoryDao.insertSearch(
                SearchHistoryEntity(
                    query = trimmed,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }

    suspend fun deleteSearch(query: String) {
        searchHistoryDao.deleteSearch(query)
    }

    suspend fun clearSearchHistory() {
        searchHistoryDao.clearAll()
    }

    fun getAllBookmarksWithEntries(): Flow<List<KnowledgeEntryWithBookmark>> {
        val entriesFlow = entryDao.getAllEntries()
        val bookmarksFlow = bookmarkDao.getAllBookmarks()
        return combine(entriesFlow, bookmarksFlow) { entries, bookmarks ->
            val entryMap = entries.associateBy { it.id }
            bookmarks.mapNotNull { bm ->
                val entry = entryMap[bm.entryId]
                entry?.let {
                    KnowledgeEntryWithBookmark(
                        entry = it,
                        isBookmarked = true,
                        privateNote = bm.privateNote
                    )
                }
            }
        }
    }

    suspend fun toggleBookmark(entryId: String) {
        val existing = bookmarkDao.getBookmarkByEntryId(entryId)
        if (existing != null) {
            bookmarkDao.deleteBookmarkByEntryId(entryId)
        } else {
            bookmarkDao.insertBookmark(
                BookmarkEntity(
                    id = "bm_$entryId",
                    entryId = entryId,
                    savedAt = System.currentTimeMillis()
                )
            )
        }
    }

    suspend fun savePrivateNote(entryId: String, note: String) {
        val existing = bookmarkDao.getBookmarkByEntryId(entryId)
        if (existing != null) {
            bookmarkDao.updateNote(entryId, note)
        } else {
            bookmarkDao.insertBookmark(
                BookmarkEntity(
                    id = "bm_$entryId",
                    entryId = entryId,
                    savedAt = System.currentTimeMillis(),
                    privateNote = note
                )
            )
        }
    }
}

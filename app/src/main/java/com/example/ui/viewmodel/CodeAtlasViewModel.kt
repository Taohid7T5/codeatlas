package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.AutocompleteSuggestion
import com.example.data.model.KnowledgeEntryEntity
import com.example.data.model.KnowledgeEntryWithBookmark
import com.example.data.model.LanguageEntity
import com.example.data.model.SearchHistoryEntity
import com.example.data.repository.CodeAtlasRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiFilters(
    val query: String = "",
    val languageSlug: String? = null,
    val category: String? = null,
    val difficulty: String? = null
)

class CodeAtlasViewModel(
    private val repository: CodeAtlasRepository
) : ViewModel() {

    private val geminiService = com.example.data.service.GeminiAiService()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _filters = MutableStateFlow(UiFilters())
    val filters: StateFlow<UiFilters> = _filters.asStateFlow()

    private val _autocompleteSuggestions = MutableStateFlow<List<AutocompleteSuggestion>>(emptyList())
    val autocompleteSuggestions: StateFlow<List<AutocompleteSuggestion>> = _autocompleteSuggestions.asStateFlow()

    private val _isDarkTheme = MutableStateFlow(true)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme.asStateFlow()

    private val _selectedEntryId = MutableStateFlow<String?>(null)
    val selectedEntryId: StateFlow<String?> = _selectedEntryId.asStateFlow()

    // AI Problem Solver State
    private val _aiQuery = MutableStateFlow("")
    val aiQuery: StateFlow<String> = _aiQuery.asStateFlow()

    private val _aiSolution = MutableStateFlow<com.example.data.service.AiSolution?>(null)
    val aiSolution: StateFlow<com.example.data.service.AiSolution?> = _aiSolution.asStateFlow()

    private val _isAiLoading = MutableStateFlow(false)
    val isAiLoading: StateFlow<Boolean> = _isAiLoading.asStateFlow()

    private var autocompleteJob: Job? = null

    val languages: StateFlow<List<LanguageEntity>> = repository.getAllLanguages()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val trendingEntries: StateFlow<List<KnowledgeEntryEntity>> = repository.getTrendingEntries()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val recentSearches: StateFlow<List<SearchHistoryEntity>> = repository.getRecentSearches()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val bookmarks: StateFlow<List<KnowledgeEntryWithBookmark>> = repository.getAllBookmarksWithEntries()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val searchResults: StateFlow<List<KnowledgeEntryWithBookmark>> = _filters
        .flatMapLatest { f ->
            repository.searchEntries(
                query = f.query,
                languageSlug = f.languageSlug,
                contentType = f.category,
                difficulty = f.difficulty
            )
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val selectedEntryWithBookmark: StateFlow<KnowledgeEntryWithBookmark?> = _selectedEntryId
        .flatMapLatest { id ->
            if (id == null) MutableStateFlow(null)
            else repository.observeEntryWithBookmark(id)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun onQueryChange(newQuery: String) {
        _searchQuery.value = newQuery

        // Cancel existing job and debounce autocomplete search
        autocompleteJob?.cancel()
        if (newQuery.isBlank()) {
            _autocompleteSuggestions.value = emptyList()
        } else {
            autocompleteJob = viewModelScope.launch {
                delay(80) // Fast, snappy response
                val suggestions = repository.getAutocompleteSuggestions(newQuery)
                _autocompleteSuggestions.value = suggestions
            }
        }
    }

    fun performSearch(queryToSearch: String) {
        val trimmed = queryToSearch.trim()
        _searchQuery.value = trimmed
        _filters.update { it.copy(query = trimmed) }
        _autocompleteSuggestions.value = emptyList()

        if (trimmed.isNotBlank()) {
            viewModelScope.launch {
                repository.recordSearch(trimmed)
            }
        }
    }

    fun setLanguageFilter(slug: String?) {
        _filters.update { it.copy(languageSlug = if (it.languageSlug == slug) null else slug) }
    }

    fun setCategoryFilter(category: String?) {
        _filters.update { it.copy(category = if (it.category == category) null else category) }
    }

    fun setDifficultyFilter(difficulty: String?) {
        _filters.update { it.copy(difficulty = if (it.difficulty == difficulty) null else difficulty) }
    }

    fun clearFilters() {
        _filters.update { UiFilters(query = _searchQuery.value) }
    }

    fun clearAllSearchAndFilters() {
        _searchQuery.value = ""
        _filters.value = UiFilters()
        _autocompleteSuggestions.value = emptyList()
    }

    fun selectEntry(entryId: String) {
        _selectedEntryId.value = entryId
    }

    fun clearSelectedEntry() {
        _selectedEntryId.value = null
    }

    fun toggleBookmark(entryId: String) {
        viewModelScope.launch {
            repository.toggleBookmark(entryId)
        }
    }

    fun savePrivateNote(entryId: String, note: String) {
        viewModelScope.launch {
            repository.savePrivateNote(entryId, note)
        }
    }

    fun deleteRecentSearch(query: String) {
        viewModelScope.launch {
            repository.deleteSearch(query)
        }
    }

    fun clearRecentSearches() {
        viewModelScope.launch {
            repository.clearSearchHistory()
        }
    }

    fun toggleTheme() {
        _isDarkTheme.update { !it }
    }

    fun updateAiQuery(query: String) {
        _aiQuery.value = query
    }

    fun askAi(prompt: String) {
        val trimmed = prompt.trim()
        if (trimmed.isBlank()) return
        _aiQuery.value = trimmed
        _isAiLoading.value = true

        viewModelScope.launch {
            val result = geminiService.solveProblem(trimmed)
            _isAiLoading.value = false
            result.onSuccess { solution ->
                _aiSolution.value = solution
            }
        }
    }

    fun clearAi() {
        _aiQuery.value = ""
        _aiSolution.value = null
        _isAiLoading.value = false
    }
}

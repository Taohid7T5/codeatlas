package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.BookmarkEntity
import com.example.data.model.KnowledgeEntryEntity
import com.example.data.model.LanguageEntity
import com.example.data.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {
    @Query("SELECT * FROM languages ORDER BY name ASC")
    fun getAllLanguages(): Flow<List<LanguageEntity>>

    @Query("SELECT * FROM languages WHERE slug = :slug LIMIT 1")
    suspend fun getLanguageBySlug(slug: String): LanguageEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(languages: List<LanguageEntity>)
}

@Dao
interface KnowledgeEntryDao {
    @Query("SELECT * FROM knowledge_entries ORDER BY popularityScore DESC")
    fun getAllEntries(): Flow<List<KnowledgeEntryEntity>>

    @Query("SELECT * FROM knowledge_entries WHERE id = :id LIMIT 1")
    suspend fun getEntryById(id: String): KnowledgeEntryEntity?

    @Query("SELECT * FROM knowledge_entries WHERE isTrending = 1 ORDER BY popularityScore DESC LIMIT 10")
    fun getTrendingEntries(): Flow<List<KnowledgeEntryEntity>>

    @Query("""
        SELECT * FROM knowledge_entries 
        WHERE (:query = '' OR title LIKE '%' || :query || '%' OR tags LIKE '%' || :query || '%' OR definition LIKE '%' || :query || '%' OR syntax LIKE '%' || :query || '%')
        AND (:languageSlug IS NULL OR languageSlug = :languageSlug)
        AND (:contentType IS NULL OR contentType = :contentType)
        AND (:difficulty IS NULL OR difficulty = :difficulty)
        ORDER BY 
           CASE WHEN title = :query THEN 1
                WHEN title LIKE :query || '%' THEN 2
                WHEN title LIKE '%' || :query || '%' THEN 3
                ELSE 4
           END,
           popularityScore DESC
    """)
    fun searchEntries(
        query: String,
        languageSlug: String?,
        contentType: String?,
        difficulty: String?
    ): Flow<List<KnowledgeEntryEntity>>

    @Query("""
        SELECT * FROM knowledge_entries
        WHERE title LIKE '%' || :query || '%'
           OR tags LIKE '%' || :query || '%'
           OR syntax LIKE '%' || :query || '%'
        ORDER BY 
           CASE WHEN title LIKE :query || '%' THEN 1 ELSE 2 END,
           popularityScore DESC
        LIMIT 10
    """)
    suspend fun getAutocompleteMatches(query: String): List<KnowledgeEntryEntity>

    @Query("SELECT * FROM knowledge_entries WHERE languageSlug = :languageSlug ORDER BY popularityScore DESC")
    fun getEntriesByLanguage(languageSlug: String): Flow<List<KnowledgeEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<KnowledgeEntryEntity>)

    @Query("SELECT COUNT(*) FROM knowledge_entries")
    suspend fun getCount(): Int
}

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY savedAt DESC")
    fun getAllBookmarks(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarks WHERE entryId = :entryId LIMIT 1")
    suspend fun getBookmarkByEntryId(entryId: String): BookmarkEntity?

    @Query("SELECT * FROM bookmarks WHERE entryId = :entryId LIMIT 1")
    fun observeBookmarkByEntryId(entryId: String): Flow<BookmarkEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE entryId = :entryId")
    suspend fun deleteBookmarkByEntryId(entryId: String)

    @Query("UPDATE bookmarks SET privateNote = :note WHERE entryId = :entryId")
    suspend fun updateNote(entryId: String, note: String)
}

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history ORDER BY timestamp DESC LIMIT 15")
    fun getRecentSearches(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(history: SearchHistoryEntity)

    @Query("DELETE FROM search_history WHERE query = :query")
    suspend fun deleteSearch(query: String)

    @Query("DELETE FROM search_history")
    suspend fun clearAll()
}

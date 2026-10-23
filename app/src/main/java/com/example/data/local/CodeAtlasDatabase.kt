package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.BookmarkEntity
import com.example.data.model.KnowledgeEntryEntity
import com.example.data.model.LanguageEntity
import com.example.data.model.SearchHistoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        LanguageEntity::class,
        KnowledgeEntryEntity::class,
        BookmarkEntity::class,
        SearchHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class CodeAtlasDatabase : RoomDatabase() {
    abstract fun languageDao(): LanguageDao
    abstract fun knowledgeEntryDao(): KnowledgeEntryDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: CodeAtlasDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): CodeAtlasDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CodeAtlasDatabase::class.java,
                    "codeatlas_database"
                )
                    .addCallback(DatabaseCallback(scope))
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database)
                    }
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        database.languageDao().insertAll(PrepopulatedData.languages)
                        database.knowledgeEntryDao().insertAll(PrepopulatedData.entries)
                    }
                }
            }

            suspend fun populateDatabase(database: CodeAtlasDatabase) {
                database.languageDao().insertAll(PrepopulatedData.languages)
                database.knowledgeEntryDao().insertAll(PrepopulatedData.entries)
                // Seed initial sample search history
                database.searchHistoryDao().insertSearch(
                    SearchHistoryEntity(query = "async/await", timestamp = System.currentTimeMillis() - 3600000)
                )
                database.searchHistoryDao().insertSearch(
                    SearchHistoryEntity(query = "reverse string python", timestamp = System.currentTimeMillis() - 7200000)
                )
                database.searchHistoryDao().insertSearch(
                    SearchHistoryEntity(query = "SQL joins", timestamp = System.currentTimeMillis() - 10800000)
                )
            }
        }
    }
}

package com.example.genericadapter

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.genericadapter.model.Language
import com.example.genericadapter.room.LanguageDao

@Database(
    entities = [Language::class],
    version = 2,
    exportSchema = true
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun languageDatabase(): LanguageDao

    companion object {

        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            if (INSTANCE == null) {
                synchronized(this) {
                    // Pass the database to the INSTANCE
                    INSTANCE = buildDatabase(context)
                }
            }
            // Return database.
            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): NoteDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "notes_database"
            )
                .build()
        }
    }
}
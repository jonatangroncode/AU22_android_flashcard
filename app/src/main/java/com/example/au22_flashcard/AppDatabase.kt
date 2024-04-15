package com.example.au22_flashcard // Package declaration

import android.content.Context // Import Android Context class
import androidx.room.Database // Import Room Database annotation
import androidx.room.Room // Import Room class
import androidx.room.RoomDatabase // Import RoomDatabase class

@Database(entities = [Word::class], version = 1) // Annotation to define Room database
abstract class AppDatabase : RoomDatabase() { // Abstract class representing Room database

    abstract val wordDao : WordDao // Abstract property to access WordDao

    companion object { // Companion object for singleton pattern

        @Volatile
        private var INSTANCE : AppDatabase? = null // Volatile variable for database instance

        // Method to get singleton instance of database
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) { // Synchronized block to ensure thread safety
                var instance = INSTANCE // Retrieve instance from volatile variable

                if (instance == null) { // Check if instance is null
                    instance = Room.databaseBuilder( // Create new database instance
                        context.applicationContext, // Context
                        AppDatabase::class.java, // Database class
                        "word-items" // Database name
                    )
                        .fallbackToDestructiveMigration() // Allow destructive migration
                        .build() // Build database
                    INSTANCE = instance // Assign instance to volatile variable
                }

                return instance // Return instance of database
            }
        }
    }
}
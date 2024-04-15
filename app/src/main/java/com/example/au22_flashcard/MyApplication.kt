package com.example.au22_flashcard
import android.app.Application
import androidx.room.Room

class MyApplication : Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java, "word-items")
            .fallbackToDestructiveMigration()
            .build()
    }
}

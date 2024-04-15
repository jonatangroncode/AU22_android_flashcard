package com.example.au22_flashcard

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Index
import androidx.room.Insert
import androidx.room.Query
import java.util.Locale.Category


@Dao
interface WordDao {

    @Insert
    fun insert(word: Word)

    @Delete
    fun delete(word: Word)

    @Query("SELECT * FROM word_table")
    fun getAll(): List<Word>

    @Query("SELECT * FROM word_table WHERE id LIKE :type")
    fun findByCategory(type: String): List<Word>


    // delete

    // getAllwords

}
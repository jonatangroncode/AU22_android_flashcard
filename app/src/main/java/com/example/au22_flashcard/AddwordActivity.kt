package com.example.au22_flashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AddwordActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job : Job
    private lateinit var db : AppDatabase
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addword)
        job = Job()

        val addingWordButton = findViewById<Button>(R.id.addingwordbtn)
        addingWordButton.setOnClickListener {
            val swedishWord = findViewById<EditText>(R.id.editTextText).text.toString()
            val englishWord = findViewById<EditText>(R.id.editTextText2).text.toString()

            if (swedishWord.isNotEmpty() && englishWord.isNotEmpty()) { // Kontrollera om båda textrutorna är ifyllda
                // Create a Word object with the entered text
                val newWord = Word(0, swedishWord, englishWord)

                // Save the word to the database
                saveItem(newWord)
            } else {
                // Om någon av textrutorna är tom, visa ett meddelande till användaren
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            }
        }



        db = (application as MyApplication).database

        val word1 = Word(0, "play","spela")
        val word2 = Word(0, "house","hus")
        val word3 = Word(0, "image","bild")

       // saveItem(word1)
       // saveItem(word2)
        // saveItem(word3)

        val list = loadAllItems()


        launch {
            val wordList = list.await()

            for(word in wordList) {
                Log.d("!!!", "word: $word")
            }

        }


    }

    fun delete(word : Word) =
        launch(Dispatchers.IO) {
            db.wordDao.delete(word)
        }

    fun loadByCategory(category : String) : Deferred<List<Word>> =
        async(Dispatchers.IO) {
            db.wordDao.findByCategory(category)
        }

    fun loadAllItems() : Deferred<List<Word>> =
        async(Dispatchers.IO) {
            db.wordDao.getAll()
        }
    fun saveItem(word: Word) {
        launch(Dispatchers.IO) {
            db.wordDao.insert(word)

        }
    }
}
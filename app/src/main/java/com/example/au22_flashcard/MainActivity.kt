package com.example.au22_flashcard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    private lateinit var db: AppDatabase
    private lateinit var wordView: TextView
    private var currentWord: Word? = null
    private lateinit var adapter: WordRecycleAdapter

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = Job()

        db = (application as MyApplication).database

        val list = loadAllItems()


        launch {
            val wordList = list.await()

            for(word in wordList) {
                Log.d("!!!", "word: $word")
            }

        }

        db = AppDatabase.getInstance(this)

        wordView = findViewById(R.id.wordTextView)

        setupRecyclerView()

        val addWordButton = findViewById<Button>(R.id.addwordbutton)
        addWordButton.setOnClickListener {
            val intent = Intent(this, AddwordActivity::class.java)
            startActivity(intent)
        }

        wordView.setOnClickListener {
            revealTranslation()
        }
        showNewWord()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.wordRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = WordRecycleAdapter(this, listOf()) // Initially, no data
        recyclerView.adapter = adapter
    }

    private fun revealTranslation() {
        wordView.text = currentWord?.english
    }

    private fun showNewWord() {
        launch {
            try {
                val words = loadAllItems().await()
                if (words.isNotEmpty()) {
                    adapter.updateWords(words) // Update the RecyclerView with new data
                    currentWord = words.random()
                    wordView.text = currentWord?.swedish
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error loading data: ${e.message}")
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            showNewWord()
        }
        return true
    }

    private fun loadAllItems(): Deferred<List<Word>> =
        async(Dispatchers.IO) {
            db.wordDao.getAll()
        }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel() // Cancel all coroutines when the activity is destroyed to avoid memory leaks
    }
}


//Vad ska göras:

//1. skapa en ny aktivitet där ett nytt ord får skrivas in
//2. spara det nya ordet i databasen.

//3. I main activity läs in alla ord från databasen

// (anväd coroutiner när ni läser och skriver till databasen se tidigare exempel)
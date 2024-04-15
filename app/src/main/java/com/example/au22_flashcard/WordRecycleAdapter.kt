package com.example.au22_flashcard

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordRecycleAdapter(val context: Context, var words: List<Word>) :
    RecyclerView.Adapter<WordRecycleAdapter.ViewHolder>() {

    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item, parent, false)
        Log.d("!!!", "oncreateviewholder")
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return words.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("!!!Adapter", "onbindingviewholder")
        val word = words[position]
        holder.englishTextView.text = word.english
        holder.swedishTextView.text = word.swedish
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var englishTextView = itemView.findViewById<TextView>(R.id.englishTextView)
        var swedishTextView = itemView.findViewById<TextView>(R.id.swedishTextView)
    }

    fun updateWords(newWords: List<Word>) {
        words = newWords
        notifyDataSetChanged()
    }
}

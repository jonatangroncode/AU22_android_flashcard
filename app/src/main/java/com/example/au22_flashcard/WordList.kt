package com.example.au22_flashcard

class WordList() {
    private val wordList = mutableListOf<Word>()
    private val usedWords = mutableListOf<Word>()

    init {
        initializeWords()
    }


    fun initializeWords() {
        val word = Word(0,"Hello", "Hej")
        wordList.add(word)
        wordList.add(Word(1,"Good bye", "Hej då"))
        wordList.add(Word(2,"Thank you", "Tack"))
        wordList.add(Word(3,"Welcome", "Välkommen"))
        wordList.add(Word(4,"Computer", "Dator"))

    }

//    fun getNewWord() : Word {
//        val rnd = (0 until wordList.size).random()
//        return wordList[rnd]
//    }


    // alternativ 3
//    fun getNewWord() : Word {
//        if(wordList.isEmpty() ) {
//            initializeWords()
//        }
//
//        val rnd = (0 until wordList.size).random()
//        val word = wordList.removeAt(rnd)
//
//        return word
//    }

    //alternativ 1
    fun getNewWord() : Word {
        if (wordList.size == usedWords.size) {
            usedWords.clear()
        }

        var word : Word? = null

        do {
            val rnd = (0 until wordList.size).random()
            word = wordList[rnd]
        } while(usedWords.contains(word))

        usedWords.add(word!!)

        return word
    }



}









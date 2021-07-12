package ru.geekbrains.dictionary.navigation

import ru.geekbrains.dictionary.view.historyscreen.HistoryFragment
import ru.geekbrains.wordslistscreen.wordslist.WordsListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class WordsListScreen() : SupportAppScreen() {
        override fun getFragment() = WordsListFragment.newInstance()
    }
    class DescriptionScreen(val word: String, val description: String, val pictureUrl: String?): SupportAppScreen() {
        override fun getFragment() = ru.geekbrains.dictionary.descriptionscreen.DescriptionFragment.newInstance(word, description, pictureUrl)
    }

    class HistoryScreen() : SupportAppScreen() {
        override fun getFragment() = HistoryFragment.newInstance()
    }
}
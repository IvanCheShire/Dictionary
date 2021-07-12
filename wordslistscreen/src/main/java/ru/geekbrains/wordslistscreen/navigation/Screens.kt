package ru.geekbrains.wordslistscreen.navigation

import ru.geekbrains.historyscreen.HistoryFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {


    class DescriptionScreen(val word: String, val description: String, val pictureUrl: String?): SupportAppScreen() {
        override fun getFragment() = ru.geekbrains.descriptionscreen.DescriptionFragment.newInstance(word, description, pictureUrl)
    }

    class HistoryScreen() : SupportAppScreen() {
        override fun getFragment() = HistoryFragment.newInstance()
    }


}
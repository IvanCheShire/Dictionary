package ru.geekbrains.wordslistscreen.navigation

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {


    class DescriptionScreen(val word: String, val description: String, val pictureUrl: String?): SupportAppScreen() {
        override fun getFragment() = ru.geekbrains.descriptionscreen.DescriptionFragment.newInstance(word, description, pictureUrl)
    }

    class NewModuleHistoryScreen(val historyFragment: Any) : SupportAppScreen(){
        override fun getFragment() = historyFragment as? Fragment
    }


}
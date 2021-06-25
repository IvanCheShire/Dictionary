package ru.geekbrains.dictionary.presenter

import ru.geekbrains.dictionary.model.data.AppState
import ru.geekbrains.dictionary.view.base.View
import ru.geekbrains.dictionary.view.main.MainPresenterImpl
import ru.geekbrains.dictionary.view.wordslist.WordsListPresenterImpl
import ru.terrakok.cicerone.Router

class PresenterHolder {
    companion object{
        private var mainPresenter: MainActivityPresenter<View>? = null
        private var wordsListPresenter: FragmentPresenter<AppState, View>? = null
    }

    fun getMainPresenter(router: Router):  MainActivityPresenter<View> {
        if (mainPresenter == null){
            mainPresenter = MainPresenterImpl<View>(router)
        }
        return mainPresenter as MainActivityPresenter<View>
    }

    fun clearMainPresenter(){
        mainPresenter = null
    }

    fun getWordsListPresenter():  FragmentPresenter<AppState, View> {
        if (wordsListPresenter == null){
            wordsListPresenter = WordsListPresenterImpl<AppState, View>()
        }
        return wordsListPresenter as FragmentPresenter<AppState, View>
    }

    fun clearWordsListPresenter(){
        wordsListPresenter = null
    }
}
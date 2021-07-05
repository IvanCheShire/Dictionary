package ru.geekbrains.dictionary.view.main

import ru.geekbrains.dictionary.navigation.Screens
import ru.geekbrains.dictionary.presenter.MainActivityPresenter
import ru.geekbrains.dictionary.view.App
import ru.geekbrains.dictionary.view.base.View
import ru.terrakok.cicerone.Router

class MainPresenterImpl<V: View>(private val router: Router = App.instance.router): MainActivityPresenter<V> {

    override fun backClick() {
        router.exit()
    }

    override fun onCreate() {
        router.replaceScreen(Screens.WordsListScreen())
    }

    private var currentView: V? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
        }
    }

    override fun detachView(view: V) {
        if (view == currentView) {
            currentView = null
        }
    }
}
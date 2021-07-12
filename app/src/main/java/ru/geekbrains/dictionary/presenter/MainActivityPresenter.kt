package ru.geekbrains.dictionary.presenter

import ru.geekbrains.dictionary.view.base.View

interface MainActivityPresenter<V: View> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun onCreate()

    fun backClick()
}
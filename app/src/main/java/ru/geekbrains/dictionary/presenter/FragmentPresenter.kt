package ru.geekbrains.dictionary.presenter

import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.DataModel
import ru.geekbrains.dictionary.view.base.View

interface FragmentPresenter<T : AppState, V : View> {

    fun attachView(view: V)

    fun detachView(view: V)

    fun getData(word: String, isOnline: Boolean)

    fun backClick(): Boolean

    fun dataObtained(dataModel: List<DataModel>)

}
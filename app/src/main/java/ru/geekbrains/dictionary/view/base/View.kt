package ru.geekbrains.dictionary.view.base

import ru.geekbrains.dictionary.model.data.AppState

interface View {

    fun renderData(appState: AppState)

}
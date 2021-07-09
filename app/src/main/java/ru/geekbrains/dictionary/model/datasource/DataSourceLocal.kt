package ru.geekbrains.dictionary.model.datasource

import ru.geekbrains.dictionary.model.data.AppState

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)

}
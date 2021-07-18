package ru.geekbrains.repository.datasourse

import ru.geekbrains.model.data.AppState

interface DataSourceLocal<T> : DataSource<T> {

    suspend fun saveToDB(appState: AppState)

}
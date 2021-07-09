package ru.geekbrains.dictionary.model.repository

import ru.geekbrains.dictionary.model.data.AppState

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)

}
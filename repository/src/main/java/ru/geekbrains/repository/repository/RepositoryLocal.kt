package ru.geekbrains.repository.repository

import ru.geekbrains.model.data.AppState

interface RepositoryLocal<T> : Repository<T> {

    suspend fun saveToDB(appState: AppState)

}
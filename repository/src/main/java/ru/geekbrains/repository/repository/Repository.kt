package ru.geekbrains.repository.repository

import ru.geekbrains.model.data.DataModel


interface Repository<T> {

    suspend fun getData(word: String): List<DataModel>?
}
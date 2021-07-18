package ru.geekbrains.repository.datasourse

import ru.geekbrains.model.data.DataModel


interface DataSource<T> {

    suspend fun getData(word: String): List<DataModel>?
}
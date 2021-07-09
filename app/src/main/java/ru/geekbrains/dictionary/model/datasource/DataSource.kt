package ru.geekbrains.dictionary.model.datasource

import ru.geekbrains.dictionary.model.data.DataModel


interface DataSource<T> {

    suspend fun getData(word: String): List<DataModel>?
}
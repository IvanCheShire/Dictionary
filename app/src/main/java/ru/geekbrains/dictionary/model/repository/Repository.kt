package ru.geekbrains.dictionary.model.repository

import ru.geekbrains.dictionary.model.data.DataModel


interface Repository<T> {

    suspend fun getData(word: String): List<DataModel>?
}
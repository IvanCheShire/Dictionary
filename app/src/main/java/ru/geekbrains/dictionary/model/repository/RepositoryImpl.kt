package ru.geekbrains.dictionary.model.repository

import ru.geekbrains.dictionary.model.data.DataModel
import ru.geekbrains.dictionary.model.datasource.DataSource

class RepositoryImpl (private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        return dataSource.getData(word)
    }
}
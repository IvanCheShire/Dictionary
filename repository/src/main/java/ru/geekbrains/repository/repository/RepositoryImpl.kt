package ru.geekbrains.repository.repository

import ru.geekbrains.model.data.DataModel
import ru.geekbrains.repository.datasourse.DataSource

class RepositoryImpl (private val dataSource: DataSource<List<DataModel>>) :
    Repository<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel>? {
        return dataSource.getData(word)
    }
}
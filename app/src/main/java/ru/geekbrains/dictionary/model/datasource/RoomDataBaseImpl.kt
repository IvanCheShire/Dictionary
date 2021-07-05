package ru.geekbrains.dictionary.model.datasource

import ru.geekbrains.dictionary.model.data.DataModel

class RoomDataBaseImpl : DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> {
        TODO("not implemented")
    }
}
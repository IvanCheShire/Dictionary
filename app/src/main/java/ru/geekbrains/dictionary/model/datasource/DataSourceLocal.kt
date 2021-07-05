package ru.geekbrains.dictionary.model.datasource

import ru.geekbrains.dictionary.model.data.DataModel

class DataSourceLocal(private val remoteProvider: RoomDataBaseImpl = RoomDataBaseImpl()) :
    DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)

}
}
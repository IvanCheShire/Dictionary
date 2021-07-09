package ru.geekbrains.dictionary.model.datasource

import ru.geekbrains.dictionary.model.data.DataModel

class DataSourceRemote(private val remoteProvider: RetrofitImpl = RetrofitImpl()) :
    DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
}
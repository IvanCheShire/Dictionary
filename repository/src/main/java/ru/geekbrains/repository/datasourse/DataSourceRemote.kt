package ru.geekbrains.repository.datasourse

import ru.geekbrains.model.data.DataModel
import ru.geekbrains.repository.datasourse.network.RetrofitImpl

class DataSourceRemote(private val remoteProvider: RetrofitImpl = RetrofitImpl()) :
    ru.geekbrains.repository.datasourse.DataSource<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel> = remoteProvider.getData(word)
}
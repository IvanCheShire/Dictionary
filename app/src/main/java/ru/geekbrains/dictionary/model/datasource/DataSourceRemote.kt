package ru.geekbrains.dictionary.model.datasource

import io.reactivex.Observable
import ru.geekbrains.dictionary.model.data.DataModel

class DataSourceRemote(private val remoteProvider: RetrofitImpl = RetrofitImpl()) :
    DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> = remoteProvider.getData(word)
}
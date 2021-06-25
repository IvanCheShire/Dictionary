package ru.geekbrains.dictionary.model.datasource

import io.reactivex.Observable
import ru.geekbrains.dictionary.model.data.DataModel

class RoomDataBaseImpl : DataSource<List<DataModel>> {

    override fun getData(word: String): Observable<List<DataModel>> {
        TODO("not implemented")
    }
}
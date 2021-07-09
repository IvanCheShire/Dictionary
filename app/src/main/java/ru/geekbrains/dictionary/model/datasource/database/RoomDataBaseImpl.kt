package ru.geekbrains.dictionary.model.datasource.database

import ru.geekbrains.dictionary.model.data.AppState
import ru.geekbrains.dictionary.model.data.DataModel
import ru.geekbrains.dictionary.model.datasource.DataSourceLocal
import ru.geekbrains.dictionary.model.datasource.database.room.HistoryDao

class RoomDataBaseImpl(private val historyDao: HistoryDao) :
    DataSourceLocal<List<DataModel>> {

    override suspend fun getData(word: String): List<DataModel>? {
        if (word == "") return mapHistoryEntityToSearchResult(historyDao.all())
        val historyEntityResult = historyDao.getDataByWord(word)
        return historyEntityResult?.let { mapHistoryEntityToSearchResult(listOf(it)) }
    }

    override suspend fun saveToDB(appState: AppState) {
        convertDataModelSuccessToEntity(appState)?.let {
            historyDao.insert(it)
        }
    }
}
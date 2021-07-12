package ru.geekbrains.model.data.datasource.database

import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.DataModel
import ru.geekbrains.repository.datasourse.database.DataSourceLocal
import ru.geekbrains.repository.datasourse.database.HistoryDao

class RoomDataBaseImpl(private val historyDao: ru.geekbrains.repository.datasourse.database.HistoryDao) :
    ru.geekbrains.repository.datasourse.database.DataSourceLocal<List<DataModel>> {

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
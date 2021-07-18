package ru.geekbrains.repository.datasourse.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(HistoryEntity::class), version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao(): ru.geekbrains.repository.datasourse.database.HistoryDao
}
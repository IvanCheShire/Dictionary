package ru.geekbrains.dictionary.di.modules

import dagger.Module
import dagger.Provides
import ru.geekbrains.dictionary.di.NAME_LOCAL
import ru.geekbrains.dictionary.di.NAME_REMOTE
import ru.geekbrains.dictionary.model.data.DataModel
import ru.geekbrains.dictionary.model.datasource.DataSource
import ru.geekbrains.dictionary.model.datasource.RetrofitImpl
import ru.geekbrains.dictionary.model.datasource.database.RoomDataBaseImpl
import ru.geekbrains.dictionary.model.repository.Repository
import ru.geekbrains.dictionary.model.repository.RepositoryImpl
import javax.inject.Named
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideRepositoryRemote(@Named(NAME_REMOTE) dataSourceRemote: DataSource<List<DataModel>>): Repository<List<DataModel>> =
        RepositoryImpl(dataSourceRemote)

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideRepositoryLocal(@Named(NAME_LOCAL) dataSourceLocal: DataSource<List<DataModel>>): Repository<List<DataModel>> =
        RepositoryImpl(dataSourceLocal)

    @Provides
    @Singleton
    @Named(NAME_REMOTE)
    internal fun provideDataSourceRemote(): DataSource<List<DataModel>> =
        RetrofitImpl()

    @Provides
    @Singleton
    @Named(NAME_LOCAL)
    internal fun provideDataSourceLocal(): DataSource<List<DataModel>> = RoomDataBaseImpl()
}
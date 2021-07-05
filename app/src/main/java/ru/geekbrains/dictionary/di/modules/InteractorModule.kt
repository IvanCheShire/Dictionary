package ru.geekbrains.dictionary.di.modules

import dagger.Module
import dagger.Provides
import ru.geekbrains.dictionary.di.NAME_LOCAL
import ru.geekbrains.dictionary.di.NAME_REMOTE
import ru.geekbrains.dictionary.model.data.DataModel
import ru.geekbrains.dictionary.model.repository.Repository
import ru.geekbrains.dictionary.view.wordslist.WordsListInteractor
import javax.inject.Named

@Module
class InteractorModule {

    @Provides
    internal fun provideInteractor(
        @Named(NAME_REMOTE) repositoryRemote: Repository<List<DataModel>>,
        @Named(NAME_LOCAL) repositoryLocal: Repository<List<DataModel>>
    ) = WordsListInteractor(repositoryRemote, repositoryLocal)
}
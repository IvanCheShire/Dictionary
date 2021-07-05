package ru.geekbrains.dictionary.view.wordslist

import ru.geekbrains.dictionary.model.data.AppState
import ru.geekbrains.dictionary.model.data.DataModel
import ru.geekbrains.dictionary.model.repository.Repository
import ru.geekbrains.dictionary.viewmodel.Interactor

class WordsListInteractor(
    private val remoteRepository: Repository<List<DataModel>>,
    private val localRepository: Repository<List<DataModel>>
) : Interactor<AppState> {

    override suspend fun getData(word: String, fromRemoteSource: Boolean): AppState {
        return if (fromRemoteSource) {
            AppState.Success(remoteRepository.getData(word))
        } else {
            AppState.Success(localRepository.getData(word))
        }
    }
}
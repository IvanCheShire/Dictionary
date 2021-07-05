package ru.geekbrains.dictionary.view.wordslist

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.geekbrains.dictionary.model.data.AppState
import ru.geekbrains.dictionary.utils.parseSearchResults
import ru.geekbrains.dictionary.viewmodel.BaseViewModel
import ru.terrakok.cicerone.Router

class WordsListViewModel ( private val interactor: WordsListInteractor,
                           private val router: Router): BaseViewModel<AppState>()  {

    fun subscribe(): LiveData<AppState> = liveDataForViewToObserve

    override fun getData(word: String, isOnline: Boolean) {
        liveDataForViewToObserve.value = AppState.Loading(null)
        cancelJob()
        viewModelCoroutineScope.launch {
            getDataFromInteractor(word, isOnline)
        }
    }

    private suspend fun getDataFromInteractor(word: String, isOnline: Boolean) =
        withContext(Dispatchers.IO) {
            liveDataForViewToObserve.postValue(
                parseSearchResults(
                    interactor.getData(
                        word,
                        isOnline
                    )
                )
            )
        }

    override fun backPressed(): Boolean {
        router.exit()
        return true
    }

    override fun handleError(error: Throwable) {
        liveDataForViewToObserve.postValue(AppState.Error(error))
    }

    override fun onCleared() {
        liveDataForViewToObserve.value = AppState.Success(null)
        super.onCleared()
    }
}
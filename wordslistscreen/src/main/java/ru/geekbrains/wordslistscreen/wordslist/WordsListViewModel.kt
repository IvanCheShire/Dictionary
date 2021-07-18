package ru.geekbrains.wordslistscreen.wordslist

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.geekbrains.core.viewmodel.BaseViewModel
import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.DataModel
import ru.geekbrains.wordslistscreen.convertMeaningsToString
import ru.geekbrains.wordslistscreen.navigation.Screens
import ru.geekbrains.wordslistscreen.parseSearchResults
import ru.terrakok.cicerone.Router

class WordsListViewModel (private val interactor: WordsListInteractor,
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
    fun wordClicked(data: DataModel){
        router.navigateTo(
            Screens.DescriptionScreen(
            data.text!!,
            convertMeaningsToString(data.meanings!!),
            data.meanings!![0].imageUrl
        ))
        println("PICTURE URL = ${data.meanings!![0].imageUrl}")
    }

    fun historyMenuItemClicked(historyFragment: Any) {
        router.navigateTo(Screens.NewModuleHistoryScreen(historyFragment))
    }
}
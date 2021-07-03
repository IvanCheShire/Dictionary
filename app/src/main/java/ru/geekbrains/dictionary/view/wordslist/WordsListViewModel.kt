package ru.geekbrains.dictionary.view.wordslist

import androidx.lifecycle.LiveData
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import ru.geekbrains.dictionary.model.data.AppState
import ru.geekbrains.dictionary.utils.parseSearchResults
import ru.geekbrains.dictionary.viewmodel.BaseViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class WordsListViewModel @Inject constructor(private val interactor: WordsListInteractor,
                                             private val router: Router): BaseViewModel<AppState>()  {

    private var appState: AppState? = null

    fun subscribe(): LiveData<AppState> = liveDataForViewToObserve

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe(doOnSubscribe())
                .subscribeWith(getObserver())
        )
    }

    private fun doOnSubscribe(): (Disposable) -> Unit =
        { liveDataForViewToObserve.value = AppState.Loading(null) }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(state: AppState) {
                appState = parseSearchResults(state)
                liveDataForViewToObserve.value = appState
            }

            override fun onError(e: Throwable) {
                liveDataForViewToObserve.value = AppState.Error(e)
            }

            override fun onComplete() {}
        }
    }

    override fun backPressed(): Boolean {
        router.exit()
        return true
    }
}
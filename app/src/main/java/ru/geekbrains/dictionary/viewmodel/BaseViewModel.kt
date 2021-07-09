package ru.geekbrains.dictionary.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.geekbrains.dictionary.model.data.AppState
import kotlinx.coroutines.*

abstract class BaseViewModel<T : AppState>(
    protected open val liveDataForViewToObserve: MutableLiveData<T> = MutableLiveData(),
) : ViewModel() {

    protected val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleError(throwable)
        })

    abstract fun getData(word: String, isOnline: Boolean)
    abstract fun handleError(error: Throwable)

    protected fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    abstract fun backPressed(): Boolean

    override fun onCleared() {
        println("viewmodel cleared")
        super.onCleared()
        cancelJob()
    }

}
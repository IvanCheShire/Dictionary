package ru.geekbrains.wordslistscreen.wordslist

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import ru.geekbrains.model.data.AppState
import ru.geekbrains.model.data.DataModel
import ru.geekbrains.repository.datasourse.DataSourceLocal
import ru.geekbrains.repository.datasourse.DataSourceRemote
import ru.geekbrains.repository.repository.RepositoryImpl
import ru.geekbrains.dictionary.presenter.FragmentPresenter
import ru.geekbrains.dictionary.rx.SchedulerProvider
import ru.geekbrains.dictionary.view.App
import ru.geekbrains.dictionary.view.base.View
import ru.terrakok.cicerone.Router

class WordsListPresenterImpl <T : AppState, V : View>(
    private val interactor: WordsListInteractor = WordsListInteractor(
        RepositoryImpl(DataSourceRemote()),
        RepositoryImpl(DataSourceLocal())
    ),
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable(),
    protected val schedulerProvider: SchedulerProvider = SchedulerProvider(),
    private val router: Router = App.instance.router
) : FragmentPresenter<T, V> {

    private var currentView: V? = null

    private var rvData: List<DataModel>? = null

    override fun attachView(view: V) {
        if (view != currentView) {
            currentView = view
            rvData?.let { currentView?.renderData(AppState.Success(it))}
        }
    }

    override fun detachView(view: V) {
        compositeDisposable.clear()
        if (view == currentView) {
            currentView = null
        }
    }

    override fun getData(word: String, isOnline: Boolean) {
        compositeDisposable.add(
            interactor.getData(word, isOnline)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .doOnSubscribe { currentView?.renderData(AppState.Loading(null)) }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver(): DisposableObserver<AppState> {
        return object : DisposableObserver<AppState>() {

            override fun onNext(appState: AppState) {
                currentView?.renderData(appState)
            }

            override fun onError(e: Throwable) {
                currentView?.renderData(AppState.Error(e))
            }

            override fun onComplete() {
            }
        }
    }

    override fun backClick(): Boolean {
        router.exit()
        return true
    }

    override fun dataObtained(dataModel: List<DataModel>) {
        rvData = dataModel
    }


}
package ru.geekbrains.historyscreen.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.geekbrains.historyscreen.HistoryInteractor
import ru.geekbrains.historyscreen.HistoryViewModel
import ru.geekbrains.dictionary.view.main.MainActivityViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Provider



fun injectDependencies() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(listOf(viewModelModule, historyScreen))
}

val viewModelModule = module {
    single<MutableMap<Class<out ViewModel>, Provider<ViewModel>>>(qualifier = named("historyViewModelMap")) {
        val map =
            mutableMapOf(
                MainActivityViewModel::class.java to Provider<ViewModel>{ MainActivityViewModel(get<Router>()) },

                HistoryViewModel::class.java to Provider<ViewModel>{ HistoryViewModel(get<HistoryInteractor>(), get<Router>()) })
        map
    }
    single<ViewModelProvider.Factory>(qualifier = named("historyViewModelProvider")) { ViewModelFactory(get<MutableMap<Class<out ViewModel>, Provider<ViewModel>>>(
        qualifier = named("historyViewModelMap")))
    }
}

val historyScreen = module {
    factory { HistoryViewModel(get(), get()) }
    factory { HistoryInteractor(get(), get()) }
}
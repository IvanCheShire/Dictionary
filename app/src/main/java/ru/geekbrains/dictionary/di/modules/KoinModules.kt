package ru.geekbrains.dictionary.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.geekbrains.dictionary.di.NAME_LOCAL
import ru.geekbrains.dictionary.di.NAME_REMOTE
import ru.geekbrains.dictionary.di.ViewModelFactory
import ru.geekbrains.dictionary.model.data.DataModel
import ru.geekbrains.dictionary.model.datasource.RetrofitImpl
import ru.geekbrains.dictionary.model.datasource.RoomDataBaseImpl
import ru.geekbrains.dictionary.model.repository.Repository
import ru.geekbrains.dictionary.model.repository.RepositoryImpl
import ru.geekbrains.dictionary.view.main.MainActivityViewModel
import ru.geekbrains.dictionary.view.wordslist.WordsListInteractor
import ru.geekbrains.dictionary.view.wordslist.WordsListViewModel
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Provider


val application = module {
    single<Repository<List<DataModel>>>(named(NAME_REMOTE)) { RepositoryImpl(RetrofitImpl()) }
    single<Repository<List<DataModel>>>(named(NAME_LOCAL)) { RepositoryImpl(RoomDataBaseImpl()) }
}

val viewModelModule = module {
    single<MutableMap<Class<out ViewModel>, Provider<ViewModel>>> {
        var map =
            mutableMapOf(
                MainActivityViewModel::class.java to Provider<ViewModel>{MainActivityViewModel(get<Router>())},
                WordsListViewModel::class.java to Provider<ViewModel>{WordsListViewModel(get<WordsListInteractor>(), get<Router>()) })
        map
    }
    single<ViewModelProvider.Factory> { ViewModelFactory(get()) }
}

val navigation = module {
    val cicerone: Cicerone<Router> = Cicerone.create()
    factory<NavigatorHolder> { cicerone.navigatorHolder }
    factory<Router> { cicerone.router }
}

val mainActivity = module {
    factory { MainActivityViewModel(get<Router>()) }
}

val wordsListScreen = module {
    factory { WordsListInteractor(get(named(NAME_REMOTE)), get(named(NAME_LOCAL))) }
    factory { WordsListViewModel(get<WordsListInteractor>(), get<Router>()) }
}
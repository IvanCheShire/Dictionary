package ru.geekbrains.dictionary.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import org.koin.dsl.module
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import ru.geekbrains.dictionary.di.ViewModelFactory
import ru.geekbrains.model.data.DataModel
import ru.geekbrains.dictionary.model.datasource.RetrofitImpl
import ru.geekbrains.model.data.datasource.database.RoomDataBaseImpl
import ru.geekbrains.repository.repository.Repository
import ru.geekbrains.repository.repository.RepositoryImpl
import ru.geekbrains.dictionary.view.main.MainActivityViewModel
import ru.geekbrains.wordslistscreen.wordslist.WordsListInteractor
import ru.geekbrains.wordslistscreen.wordslist.WordsListViewModel
import org.koin.core.context.loadKoinModules
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Provider


fun injectDependencies() = loadModules

private val loadModules by lazy {
    loadKoinModules(listOf(application, viewModelModule, navigation, mainActivity, wordsListScreen))
}

val application = module {
    single { Room.databaseBuilder(get(), HistoryDatabase::class.java, "HistoryDB").build() }
    single<HistoryDao> { get<HistoryDatabase>().historyDao() }
    single<Repository<List<DataModel>>> { RepositoryImpl(RetrofitImpl()) }
    single<RepositoryLocal<List<DataModel>>> { RepositoryLocalImpl(RoomDataBaseImpl(get())) }
}

val viewModelModule = module {
    single<MutableMap<Class<out ViewModel>, Provider<ViewModel>>>(qualifier = named("appViewModelMap")) {
        var map =
            mutableMapOf(
                MainActivityViewModel::class.java to Provider<ViewModel>{MainActivityViewModel(get<Router>())},
                WordsListViewModel::class.java to Provider<ViewModel>{WordsListViewModel(get<WordsListInteractor>(), get<Router>()) })
        map
    }
    single<ViewModelProvider.Factory>(qualifier = named("appViewModelProvider")) {
        ViewModelFactory(get<MutableMap<Class<out ViewModel>, Provider<ViewModel>>>(
            qualifier = named("appViewModelMap")))}
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
    factory { WordsListInteractor(get(), get()) }
    factory { WordsListViewModel(get<WordsListInteractor>(), get<Router>()) }
}


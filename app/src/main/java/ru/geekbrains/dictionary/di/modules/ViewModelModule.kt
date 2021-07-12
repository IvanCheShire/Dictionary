package ru.geekbrains.dictionary.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.geekbrains.dictionary.di.ViewModelFactory
import ru.geekbrains.dictionary.di.ViewModelKey
import ru.geekbrains.dictionary.view.main.MainActivityViewModel
import ru.geekbrains.wordslistscreen.wordslist.WordsListViewModel


@Module(includes = [InteractorModule::class])
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    protected abstract fun mainActivityViewModel(mainViewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WordsListViewModel::class)
    protected abstract fun wordsListViewModel(wordsListViewModel: WordsListViewModel): ViewModel

}
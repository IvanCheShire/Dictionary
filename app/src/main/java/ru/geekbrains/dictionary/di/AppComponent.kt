package ru.geekbrains.dictionary.di

import dagger.BindsInstance
import dagger.Component
import ru.geekbrains.dictionary.di.modules.*
import ru.geekbrains.dictionary.view.App
import ru.geekbrains.dictionary.view.main.MainActivity
import ru.geekbrains.dictionary.view.wordslist.WordsListFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        InteractorModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        NavigationModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(wordsListFragment: WordsListFragment)

}
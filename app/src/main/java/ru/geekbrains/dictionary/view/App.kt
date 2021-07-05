package ru.geekbrains.dictionary.view

import ru.geekbrains.dictionary.di.modules.*
import android.app.Application
import ru.geekbrains.dictionary.di.AppComponent
import org.koin.core.context.startKoin


class App: Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            modules(listOf(application, viewModelModule, navigation, mainActivity, wordsListScreen))
        }
    }
}
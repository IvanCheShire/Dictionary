package ru.geekbrains.dictionary.view

import android.app.Application
import ru.geekbrains.dictionary.di.AppComponent


class App: Application() {

    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}
package ru.geekbrains.dictionary.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.geekbrains.dictionary.R
import ru.geekbrains.dictionary.model.data.AppState
import ru.geekbrains.dictionary.presenter.MainActivityPresenter
import ru.geekbrains.dictionary.view.App
import ru.geekbrains.dictionary.view.BackButtonListener
import ru.geekbrains.dictionary.view.base.View
import ru.terrakok.cicerone.android.support.SupportAppNavigator

class MainActivity : AppCompatActivity(), View {

    val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)

    lateinit var presenter: MainActivityPresenter<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = App.instance.presenterHolder.getMainPresenter(App.instance.router)
        presenter.onCreate()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigatorHolder.removeNavigator()
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView(this)
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        presenter.backClick()
    }

    override fun onDestroy() {
        if (isFinishing)
            App.instance.presenterHolder.clearMainPresenter()
        super.onDestroy()
    }

    override fun renderData(appState: AppState) {
//        TODO("Not yet implemented")
    }
}
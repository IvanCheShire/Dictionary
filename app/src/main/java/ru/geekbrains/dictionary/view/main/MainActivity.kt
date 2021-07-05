package ru.geekbrains.dictionary.view.main

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import ru.geekbrains.dictionary.R
import ru.geekbrains.dictionary.model.data.AppState
import ru.geekbrains.dictionary.presenter.MainActivityPresenter
import org.koin.android.ext.android.getKoin
import ru.geekbrains.dictionary.view.BackButtonListener
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator


class MainActivity : AppCompatActivity() {

    val navigatorHolder: NavigatorHolder by lazy { getKoin().get<NavigatorHolder>() }
    val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)

    val model: MainActivityViewModel by lazy {
        ViewModelProvider(this, getKoin().get()).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if(savedInstanceState == null) model.onCreate()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if (it is BackButtonListener && it.backPressed()) {
                return
            }
        }
        model.backPressed()
    }



}
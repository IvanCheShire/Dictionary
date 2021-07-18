package ru.geekbrains.dictionary.view.main

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import ru.geekbrains.dictionary.R
import ru.geekbrains.model.data.AppState
import ru.geekbrains.dictionary.presenter.MainActivityPresenter
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named
import ru.geekbrains.dictionary.view.BackButtonListener
import ru.geekbrains.dictionary.di.modules.injectDependencies
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator


class MainActivity : AppCompatActivity() {

    val navigatorHolder: NavigatorHolder by lazy { getKoin().get<NavigatorHolder>() }
    val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.container)

    lateinit var model: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        if(savedInstanceState == null) model.onCreate()
    }

    private fun initViewModel() {
        injectDependencies()
        val factory = getKoin().get<ViewModelProvider.Factory>(qualifier = named("appViewModelProvider"))
        val viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)
        model = viewModel
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
            if (it is ru.geekbrains.dictionary.core.BackButtonListener && it.backPressed()) {
                return
            }
        }



}
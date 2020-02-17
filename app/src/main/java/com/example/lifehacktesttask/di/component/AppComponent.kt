package com.example.lifehacktesttask.di.component

import com.example.lifehacktesttask.di.module.RetrofitModule
import com.example.lifehacktesttask.mvp.presenter.InfoPresenter
import com.example.lifehacktesttask.mvp.presenter.BasePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class])
interface AppComponent {
    fun inject(basePresenter: BasePresenter)
    fun inject(infoPresenter: InfoPresenter)
}
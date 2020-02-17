package com.example.lifehacktesttask.application

import android.app.Application
import android.content.Context
import com.example.lifehacktesttask.di.component.AppComponent
import com.example.lifehacktesttask.di.component.DaggerAppComponent
import com.example.lifehacktesttask.di.module.RetrofitModule


class Application : Application() {
    companion object {
        lateinit var graph: AppComponent
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        graph = DaggerAppComponent.builder().retrofitModule(RetrofitModule()).build()
    }
}
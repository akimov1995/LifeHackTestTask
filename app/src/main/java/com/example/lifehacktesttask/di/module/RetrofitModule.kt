package com.example.lifehacktesttask.di.module

import com.example.lifehacktesttask.R
import com.example.lifehacktesttask.application.Application
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class RetrofitModule {
    private val url = Application.context.resources.getString(R.string.base_url)

    @Provides
    @Singleton
    fun getRetrofitModule(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
package com.example.lifehacktesttask.mvp.view

import com.arellomobile.mvp.MvpView
import com.example.lifehacktesttask.mvp.model.Company

interface BaseView : MvpView {
    fun showCompanyList(news: List<Company>)
    fun showError(text: String)
}
package com.example.lifehacktesttask.mvp.view

import com.arellomobile.mvp.MvpView
import com.example.lifehacktesttask.mvp.model.Company

interface InfoView : MvpView {
    fun showCompanyInfo(company: Company)
    fun showError(text: String)
}
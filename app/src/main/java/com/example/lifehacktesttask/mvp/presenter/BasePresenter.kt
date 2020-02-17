package com.example.lifehacktesttask.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.lifehacktesttask.R
import com.example.lifehacktesttask.api.RestApiService
import com.example.lifehacktesttask.application.Application
import com.example.lifehacktesttask.mvp.model.Company
import com.example.lifehacktesttask.mvp.view.BaseView
import com.example.lifehacktesttask.utils.NetworkStatusChecker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

@InjectViewState
class BasePresenter : MvpPresenter<BaseView>() {

    @Inject
    lateinit var retrofit: Retrofit

    init {
        Application.graph.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
    }

    private fun loadData() {
        if (NetworkStatusChecker.isNetworkAvailable(Application.context)) {
            val service = retrofit.create(RestApiService::class.java)
            val call = service.getCompanies()

            call.enqueue(object : Callback<List<Company>> {
                override fun onResponse(
                    call: Call<List<Company>>,
                    response: Response<List<Company>>
                ) {
                    if (response.code() == 200) {
                        val companies: List<Company> = response.body()!!
                        viewState.showCompanyList(companies)
                    }
                }

                override fun onFailure(call: Call<List<Company>>, t: Throwable) {
                    viewState.showError(t.message.toString())
                }
            })
        } else {
            viewState.showError(Application.context.resources.getString(R.string.not_connection_error_message))
        }
    }
}